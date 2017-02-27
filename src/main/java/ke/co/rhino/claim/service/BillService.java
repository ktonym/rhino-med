package ke.co.rhino.claim.service;

import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.care.repo.ServiceProviderRepo;
import ke.co.rhino.claim.entity.Bill;
import ke.co.rhino.claim.entity.ClaimBatch;
import ke.co.rhino.claim.repo.BillRepo;
import ke.co.rhino.claim.repo.ClaimBatchRepo;
import ke.co.rhino.claim.repo.PreAuthBillRepo;
import ke.co.rhino.fin.entity.ExceededLimitInvoice;
import ke.co.rhino.fin.repo.ExceededLimitInvoiceRepo;
import ke.co.rhino.uw.entity.*;
import ke.co.rhino.uw.repo.*;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by akipkoech on 21/04/2016.
 */
@Service("billService")
@Transactional
public class BillService extends AbstractService implements IBillService{

    @Autowired
    private BillRepo billRepo;
    @Autowired
    private ServiceProviderRepo spRepo;
    @Autowired
    private PreAuthBillRepo preAuthBillRepo;
    @Autowired
    private CorpMemberBenefitRepo cmbRepo;
    @Autowired
    private ClaimBatchRepo claimBatchRepo;
    @Autowired
    private MemberAnniversaryRepo memberAnniversaryRepo;
    @Autowired
    private ExceededLimitInvoiceRepo exceededLimitInvoiceRepo;
    @Autowired
    private CorpAnnivRepo corpAnnivRepo;
    @Autowired
    private CorpBenefitRepo corpBenefitRepo;
    @Autowired
    private MemberRepo memberRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Bill> create(String invoiceNo,
                               LocalDate invoiceDate,
                               BigDecimal invoiceAmt,
                               BigDecimal deductionAmt,
                               String deductionReason,
                               //LocalDate enteredDate,
                               String memberNo,
                               Long idCorpAnniv,
                               Long idCorpBenefit,
                               Long idProvider,
                               String batchNo,
                               String actionUsername) {

        if(invoiceNo.trim().isEmpty()||invoiceNo==null){
            return ResultFactory.getFailResult("Cannot create bill without a valid non-null invoice number.");
        }
        if(invoiceDate==null||invoiceDate.isAfter(LocalDate.now())){
            return ResultFactory.getFailResult("Invoice date cannot be null or in the future.");
        }
        if(idProvider==null||idProvider<0){
            return ResultFactory.getFailResult("Cannot create bill without a valid non-null service provider ID.");
        }
        ServiceProvider sProvider = spRepo.findOne(idProvider);
        if(sProvider==null){
            return ResultFactory.getFailResult("No provider with ID ["+idProvider+"] was found.");
        }
        Bill existingBill = billRepo.findByProviderAndInvoiceNo(sProvider, invoiceNo);
        if(existingBill!=null){
            return ResultFactory.getFailResult("A bill from this service provider with a similar invoice no already exists.");
        }

        //CorpMemberBenefit corpMemberBenefit = cmbRepo.findByMemNo_InvcDt_Benefit(idCorpBenefit, memberNo, invoiceDate);

        CorpBenefit corpBenefit = corpBenefitRepo.findOne(idCorpBenefit);
        Member member = memberRepo.findByMemberNo(memberNo);
        MemberAnniversary ma = memberAnniversaryRepo.findBy_invoiceDate_Member(invoiceDate, member);
        // CorpMemberBenefit corpMemberBenefit = cmbRepo.findByMemNo_InvcDt_Benefit(idCorpBenefit,memberNo,invoiceDate);
        CorpMemberBenefit corpMemberBenefit = cmbRepo.findByMemberAnnivAndBenefit(ma,corpBenefit);

        if(corpMemberBenefit==null){
            return ResultFactory.getFailResult("No member benefit exists for the parameters given. Update failed.");
        }

        BigDecimal uLimit = corpBenefit.getUpperLimit(); // if this doesn't throw an expired transaction exception

        Bill.BillBuilder builder = new Bill.BillBuilder(corpMemberBenefit);

        /**
         * Enhancements to this could be:
         * (a) defining a suspension threshold
         * (b) testing utilization against this every time entry is done.
         * (c) suspending the benefit in depth (including child benefits) and breadth (for all shared instances)
         */
        BigDecimal currentUsage = getUtilization(corpMemberBenefit).add(invoiceAmt.subtract(deductionAmt));
        if(uLimit.compareTo(currentUsage)<0){
            //create an exceeded limit invoice, suspend benefit and continue to bill
            BigDecimal diff = currentUsage.subtract(uLimit);
            exceededLimitInvoice(corpMemberBenefit,diff);
            suspendBenefit(corpMemberBenefit);
            // return ResultFactory.getFailResult("Benefit limit will be exceeded.");
        } else if(uLimit.compareTo(currentUsage)==0){
            //suspend benefit and continue to bill
            suspendBenefit(corpMemberBenefit);
        }

        ClaimBatch batch = claimBatchRepo.findByBatchNo(batchNo);
        if(batch==null){
            return ResultFactory.getFailResult("The batch number supplied does not exist.");
        }

        builder.batch(batch).provider(sProvider).claimNo(generateClaimNo());
        if(deductionAmt!=null&&deductionAmt.compareTo(BigDecimal.ZERO)>0){
            builder.deductionAmt(deductionAmt);
        }

        builder.enteredDate(LocalDate.now()).invoiceDate(invoiceDate).deductionReason(deductionReason);

        Bill bill = builder.build();

        billRepo.save(bill);

        return ResultFactory.getSuccessResult(bill);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Bill> update(Long idBill,
                               String invoiceNo,
                               String claimNo,
                               LocalDate invoiceDate,
                               BigDecimal invoiceAmt,
                               BigDecimal deductionAmt,
                               String deductionReason,//LocalDate enteredDate,
                               String memberNo,
                               Long idCorpAnniv,
                               Long idCorpBenefit,
                               Long idProvider,
                               Long idClaimBatch,
                               String actionUsername) {

        if(idBill==null||idBill<0){
            return ResultFactory.getFailResult("Invalid Bill ID supplied. Update failed.");
        }

        Bill bill = billRepo.findOne(idBill);

        if(bill==null){
            return ResultFactory.getFailResult("No bill with ID ["+idBill+"] was found. Update failed.");
        }
        Bill billFromClaim = billRepo.findByClaimNo(claimNo);
        if(!bill.equals(billFromClaim)){
            return ResultFactory.getFailResult("Claim number supplied does not match the bill ID you are trying to update.");
        }

        CorpBenefit corpBenefit = corpBenefitRepo.findOne(idCorpBenefit);
        Member member = memberRepo.findByMemberNo(memberNo);
        MemberAnniversary ma = memberAnniversaryRepo.findBy_invoiceDate_Member(invoiceDate,member);
       // CorpMemberBenefit corpMemberBenefit = cmbRepo.findByMemNo_InvcDt_Benefit(idCorpBenefit,memberNo,invoiceDate);
        CorpMemberBenefit corpMemberBenefit = cmbRepo.findByMemberAnnivAndBenefit(ma,corpBenefit);

        if(corpMemberBenefit==null){
            return ResultFactory.getFailResult("No member benefit exists for the parameters given. Update failed.");
        }

//        CorpBenefit corpBenefit = corpMemberBenefit.getBenefit();

        BigDecimal uLimit = corpBenefit.getUpperLimit(); // if this doesn't throw an expired transaction exception

        /**
         * Enhancements to this could be:
         * (a) defining a suspension threshold
         * (b) testing utilization against this every time entry is done.
         * (c) suspending the benefit in depth (including child benefits) and breadth (for all shared instances)
         */
        BigDecimal currentUsage = getUtilization(corpMemberBenefit).add(bill.getInvoiceAmt().subtract(bill.getDeductionAmt()));
        if(uLimit.compareTo(currentUsage)<0){
            //create an exceeded limit invoice, suspend benefit and continue to bill
            BigDecimal diff = currentUsage.subtract(currentUsage);
            exceededLimitInvoice(corpMemberBenefit,diff);
            suspendBenefit(corpMemberBenefit);
           // return ResultFactory.getFailResult("Benefit limit will be exceeded.");
        } else if(uLimit.compareTo(getUtilization(corpMemberBenefit).add(bill.getInvoiceAmt().subtract(bill.getDeductionAmt())))==0){
            //suspend benefit and continue to bill
            suspendBenefit(corpMemberBenefit);
        }


        Bill.BillBuilder builder = new Bill.BillBuilder(corpMemberBenefit);
        builder.idBill(idBill);
        if(idClaimBatch!=null){
            ClaimBatch claimBatch = claimBatchRepo.findOne(idClaimBatch);
            if(claimBatch!=null){
                builder.batch(claimBatch);
            }
        }

        Bill updated = builder.build();
        billRepo.save(updated);

        return ResultFactory.getSuccessResult(bill);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Bill> remove(Long idBill, String actionUsername) {

        if(idBill==null||idBill<1){
            return ResultFactory.getFailResult("Invalid bill ID supplied. Deletion failed.");
        }
        Bill bill = billRepo.findOne(idBill);
        if(bill==null){
            return ResultFactory.getFailResult("No bill with ID ["+idBill+"] was found. Deletion failed.");
        }
        if(preAuthBillRepo.countByBill(bill)>0){
            return ResultFactory.getFailResult("Bill has one or more child records(LOUs) associated with it. Deletion would create orphan records.");
        }
        billRepo.delete(bill);
        String msg = "Bill with ID " + idBill + " was deleted by "+actionUsername;
        return ResultFactory.getSuccessResult(bill,msg);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Bill>> findAll(int pageNum, int size, String actionUsername) {

        PageRequest request = new PageRequest(pageNum-1,size);
        Page<Bill> billPage = billRepo.findAll(request);
        return ResultFactory.getSuccessResult(billPage);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Bill>> findByAnniv(int pageNum, int size,Long idCorpAnniv, String actionUsername) {

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);
        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found.");
        }

        PageRequest request = new PageRequest(pageNum-1,size);
        Page<Bill> billPage = billRepo.findByCorpMemberBenefit_Benefit_Category_CorpAnniv(corpAnniv,request);

        return ResultFactory.getSuccessResult(billPage);
    }


    private String generateClaimNo(){
        StringBuilder clmNo = new StringBuilder("CLM-");
        long maxClm = billRepo.count();
        maxClm++;
        return clmNo.append(maxClm).toString();
    }

    private BigDecimal getUtilization(CorpMemberBenefit corpMemberBenefit){

        List<CorpMemberBenefit> workingList = new ArrayList<>();

        if(corpMemberBenefit.getBenefit().isSharing()){
            // look up all family members that qualify for this benefit, then sum up the individual utilization
            // for each shared benefit, loop through the child benefits and check for isSharing. It's possible
            // to have a parent benefit shared by a family but a child benefit not shared..
            Member principal = corpMemberBenefit.getMemberAnniv().getMember().getPrincipal();
            CorpBenefit corpBenefit = corpMemberBenefit.getBenefit();
            List<MemberAnniversary> memberAnniversaryList = memberAnniversaryRepo.findByMember_PrincipalAndCorpAnniv(principal, corpMemberBenefit.getMemberAnniv().getCorpAnniv());
//            List<CorpMemberBenefit> familyBenefits = memberAnniversaryList.stream()
//                    .map(ben -> ben.getBenefits())
            for (MemberAnniversary ma: memberAnniversaryList){
                List<CorpMemberBenefit> familyBenefits = ma.getBenefits();
                //List<CorpMemberBenefit> filtered = new ArrayList<>();
                familyBenefits.parallelStream()
                        .filter(cmb -> cmb.getBenefit().equals(corpBenefit)).forEach(cmb -> {
                            workingList.add(cmb);
                            if (cmb.getChildMemberBenefits().size() > 0) {
                                for (CorpMemberBenefit childCMB : cmb.getChildMemberBenefits()) {
                                    workingList.add(childCMB);
                                    if (childCMB.getChildMemberBenefits().size() > 0) {
                                        workingList.addAll(childCMB.getChildMemberBenefits().parallelStream().collect(Collectors.toList()));
                                    }
                                }
                            }
                        });

                //work on the new filtered list of benefits that are now the desired, multi-level, shared corpMemberBenefits

            }


        } else {
            // sum up the bills incurred by the member(alone), including child benefits
            // No need to check for isSharing property for child benefits any longer..
            workingList.add(corpMemberBenefit);//directly incurred at the root
            if(corpMemberBenefit.getChildMemberBenefits().size()>0){
                for(CorpMemberBenefit childCMB : corpMemberBenefit.getChildMemberBenefits()){
                    workingList.add(childCMB);
                    if(childCMB.getChildMemberBenefits().size()>0){
                        workingList.addAll(childCMB.getChildMemberBenefits().parallelStream().collect(Collectors.toList()));
                    }
                }
            }

        }

        BigDecimal sum = BigDecimal.ZERO;

        for(CorpMemberBenefit benefit: workingList){
            for(Bill bill: benefit.getBills()){
                BigDecimal payableAmt = bill.getInvoiceAmt().subtract(bill.getDeductionAmt());
                sum.add(payableAmt);
            }
        }

//        BigDecimal sum = bills.stream().map(b -> (b.getInvoiceAmt().subtract(b.getDeductionAmt())))
//                .reduce(BigDecimal.ZERO, (x, y) -> x.add(y));

        return sum;

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void suspendBenefit(CorpMemberBenefit corpMemberBenefit){
        // check for shared-status
        // --if true, filter as done above
        // --else,
        List<CorpMemberBenefit> workingList = new ArrayList<>();
        CorpBenefit benefit = corpMemberBenefit.getBenefit();
        if(benefit.isSharing()){
            Member principal = corpMemberBenefit.getMemberAnniv().getMember().getPrincipal();
            List<MemberAnniversary> memberAnniversaryList = memberAnniversaryRepo.findByMember_PrincipalAndCorpAnniv(principal, corpMemberBenefit.getMemberAnniv().getCorpAnniv());

            for (MemberAnniversary ma: memberAnniversaryList){
                List<CorpMemberBenefit> familyBenefits = ma.getBenefits();
                familyBenefits.parallelStream().filter(cmb -> cmb.getBenefit().equals(benefit)).forEach(cmb -> {
                    workingList.add(cmb);
                    if (cmb.getChildMemberBenefits().size() > 0) {
                        for (CorpMemberBenefit childCMB : cmb.getChildMemberBenefits()) {
                            workingList.add(childCMB);
                            if (childCMB.getChildMemberBenefits().size() > 0) {
                                workingList.addAll(childCMB.getChildMemberBenefits().parallelStream().collect(Collectors.toList()));
                            }
                        }
                    }
                });
            }

        }else{
            workingList.add(corpMemberBenefit);
            if(corpMemberBenefit.getChildMemberBenefits().size()>0){
                for (CorpMemberBenefit childMB : corpMemberBenefit.getChildMemberBenefits()){
                    workingList.add(childMB);
                    if(childMB.getChildMemberBenefits().size()>0){
                        workingList.addAll(childMB.getChildMemberBenefits().parallelStream().collect(Collectors.toList()));
                    }
                }
            }
        }

        for(CorpMemberBenefit candidate : workingList){
            cmbRepo.updateStatus(BenefitStatus.SUSPENDED,candidate.getBenefit(),candidate.getMemberAnniv());
        }

    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void exceededLimitInvoice(CorpMemberBenefit cmb,BigDecimal amount){
        ExceededLimitInvoice xlInvoice = new ExceededLimitInvoice.ExceededLimitInvoiceBuilder(cmb,amount,LocalDate.now()).build();
        exceededLimitInvoiceRepo.save(xlInvoice);
    }

}
