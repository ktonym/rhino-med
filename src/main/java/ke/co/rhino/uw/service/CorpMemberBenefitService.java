package ke.co.rhino.uw.service;

import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.care.repo.PreAuthRepo;
import ke.co.rhino.claim.repo.BillRepo;
import ke.co.rhino.fin.repo.PremiumInvoiceItemRepo;
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

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by akipkoech on 28/06/2016.
 */
@Service("corpMemberBenefitService")
@Transactional
public class CorpMemberBenefitService extends AbstractService implements ICorpMemberBenefitService {

    @Autowired
    private CorpMemberBenefitRepo repo;
    /*@Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private BenefitRefRepo benefitRefRepo;*/
    @Autowired
    private MemberAnniversaryRepo memberAnniversaryRepo;
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private CorpAnnivRepo corpAnnivRepo;
    @Autowired
    private CorpBenefitRepo corpBenefitRepo;
    @Autowired
    private BillRepo billRepo;
    @Autowired
    private PreAuthRepo preAuthRepo;
    @Autowired
    private PremiumInvoiceItemRepo premiumInvoiceItemRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<CorpMemberBenefit> create(Long idMember,
                                            Long idCorpAnniv,
                                            Long idCorpBenefit,
                                            Optional<Long> idParentCorpBenefitOpt,
                                            BenefitStatus status,
                                            LocalDate wef,
                                            String actionUsername) {

        if(idMember==null||idMember<1){
            return ResultFactory.getFailResult("Invalid member ID supplied. Member benefit creation failed.");
        }

        if(idCorpAnniv==null||idCorpAnniv<1){
            return ResultFactory.getFailResult("Invalid corporate anniversary ID supplied. Member benefit creation failed.");
        }

        if(idCorpBenefit==null||idCorpBenefit<1){
            return ResultFactory.getFailResult("Invalid corporate benefit ID supplied. Member benefit creation failed.");
        }

        Member member = memberRepo.findOne(idMember);
        if(member==null){
            return ResultFactory.getFailResult("No member with ID [" +idMember+ "] was found. Member benefit creation failed.");
        }
        CorpAnniv anniv = corpAnnivRepo.findOne(idCorpAnniv);
        if(idCorpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found. Member benefit creation failed.");
        }
        //MemberAnnivId memberAnnivId = new MemberAnnivId(member,anniv);

        MemberAnniversary memberAnniv = memberAnniversaryRepo.findByCorpAnnivAndMember(anniv,member);
        if(memberAnniv==null){
            return ResultFactory.getFailResult("Please define a cover period (anniversary) for the member first.");
        }
        CorpBenefit corpBenefit = corpBenefitRepo.findOne(idCorpBenefit);
        if(corpBenefit==null){
            return ResultFactory.getFailResult("No corporate benefit with ID ["+idCorpBenefit+"] was found. Member benefit creation failed.");
        }

        //CorpMemberBenefitId benefitId = new CorpMemberBenefitId(corpBenefit,memberAnniv);

        /*CorpMemberBenefit testBenefit = repo.findOne(benefitId);
        if(testBenefit!=null){
            return ResultFactory.getFailResult("A member benefit has already been defined with the parameters supplied.");
        }*/

        CorpMemberBenefit.CorpMemberBenefitBuilder benefitBuilder = new CorpMemberBenefit.CorpMemberBenefitBuilder(memberAnniv,corpBenefit)
                .status(status);

        //TODO Fix this big problem
        /*if(idParentCorpBenefitOpt.isPresent()){
            Long idParentCorpBenefit = idParentCorpBenefitOpt.get();
            CorpBenefit parentBenefit = corpBenefitRepo.findOne(idParentCorpBenefit);
            // checking whether the parent member benefit being assigned has a corpBenefit that is a parent corpBenefit of this
            // member benefit
            if(corpBenefit.getParentBenefit().equals(parentBenefit)) {
                //CorpMemberBenefitId parentBenefitId = new CorpMemberBenefitId(parentBenefit, memberAnniv);
                CorpMemberBenefit parent = repo.findOne(parentBenefitId);
                benefitBuilder.parentMemberBenefit(parent);
            } else {
                return ResultFactory.getFailResult("The parent benefit you're assigning to this benefit does not match the parent-child benefit setup at the scheme level. Kindly check and revise.");
            }
        }*/

        if(wef==null){
            wef = LocalDate.now();
        } else {
            // This may result in some hibernate transaction-related exception..
            if(memberAnniv.getCorpAnniv().getInception().isAfter(wef)){
                return ResultFactory.getFailResult("Member's benefit cannot take effect before start of plan policy term.");
            }
        }

        CorpMemberBenefit benefit = benefitBuilder.wef(wef)
                .build();

        repo.save(benefit);

        return ResultFactory.getSuccessResult(benefit);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<CorpMemberBenefit>> findAll(Long idMember,
                                                   Long idCorpAnniv, int pageNum, int size,
                                                   String actionUsername) {

        if(idMember==null||idMember<1){
            return ResultFactory.getFailResult("Invalid member ID provided.");
        }
        if(idCorpAnniv==null||idCorpAnniv<1){
            return ResultFactory.getFailResult("Invalid corporate anniversary ID provided.");
        }
        Member member = memberRepo.findOne(idMember);
        if(member==null){
            return ResultFactory.getFailResult("No member with ID ["+idMember+"] was found.");
        }
        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);
        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found.");
        }
//        MemberAnnivId memberAnnivId = new MemberAnnivId(member,corpAnniv);
        MemberAnniversary memberAnniversary = memberAnniversaryRepo.findByCorpAnnivAndMember(corpAnniv,member);
        if(memberAnniversary==null){
            return ResultFactory.getFailResult("No member anniversary found with given parameters: [(memberID,"+idMember+"),(corpAnnivID,"+idCorpAnniv+")]");
        }

        PageRequest request = new PageRequest(pageNum - 1, size);
        Page<CorpMemberBenefit> page = repo.findByMemberAnniv(memberAnniversary,request);
        return ResultFactory.getSuccessResult(page);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<CorpMemberBenefit> update(Long idCorpMemberBenefit,
                                            Long idMember,
                                            Long idCorpAnniv,
                                            Long idCorpBenefit,
                                            Optional<Long> idParentCorpBenefitOpt,
                                            BenefitStatus status,
                                            LocalDate wef,
                                            String actionUsername) {

        if(idMember==null||idMember<1){
            return ResultFactory.getFailResult("Invalid member ID supplied. Member benefit update failed.");
        }

        if(idCorpAnniv==null||idCorpAnniv<1){
            return ResultFactory.getFailResult("Invalid corporate anniversary ID supplied. Member benefit update failed.");
        }

        if(idCorpBenefit==null||idCorpBenefit<1){
            return ResultFactory.getFailResult("Invalid corporate benefit ID supplied. Member benefit update failed.");
        }

        Member member = memberRepo.findOne(idMember);
        if(member==null){
            return ResultFactory.getFailResult("No member with ID [" +idMember+ "] was found. Member benefit update failed.");
        }
        CorpAnniv anniv = corpAnnivRepo.findOne(idCorpAnniv);
        if(idCorpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found. Member benefit update failed.");
        }
        //MemberAnnivId memberAnnivId = new MemberAnnivId(member,anniv);

        MemberAnniversary memberAnniv = memberAnniversaryRepo.findByCorpAnnivAndMember(anniv,member);
        if(memberAnniv==null){
            return ResultFactory.getFailResult("Please define a cover period (anniversary) for the member first.");
        }
        CorpBenefit corpBenefit = corpBenefitRepo.findOne(idCorpBenefit);
        if(corpBenefit==null){
            return ResultFactory.getFailResult("No corporate benefit with ID ["+idCorpBenefit+"] was found. Member benefit update failed.");
        }

        //CorpMemberBenefitId benefitId = new CorpMemberBenefitId(corpBenefit,memberAnniv);

        CorpMemberBenefit testBenefit = repo.findOne(idCorpMemberBenefit);

        if(testBenefit==null){
            return ResultFactory.getFailResult("No member benefit with the supplied parameters was found.");
        }

        CorpMemberBenefit.CorpMemberBenefitBuilder builder = new CorpMemberBenefit.CorpMemberBenefitBuilder(memberAnniv,corpBenefit);
        builder.status(status).wef(wef);

        //TODO fix corpBenefit -> childCorpBenefit : corpMemberBenefit -> childCorpMemberBenefit design
        /*if(idParentCorpBenefitOpt.isPresent()){
            Long idParentCorpBenefit = idParentCorpBenefitOpt.get();
            CorpBenefit parentBenefit = corpBenefitRepo.findOne(idParentCorpBenefit);
            // checking whether the parent member benefit being assigned has a corpBenefit that is a parent corpBenefit of this
            // member benefit
            if(corpBenefit.getParentBenefit().equals(parentBenefit)) {
                CorpMemberBenefitId parentBenefitId = new CorpMemberBenefitId(parentBenefit, memberAnniv);
                CorpMemberBenefit parent = repo.findOne(parentBenefitId);
                builder.parentMemberBenefit(parent);
            } else {
                return ResultFactory.getFailResult("The parent benefit you're assigning to this benefit does not match the parent-child benefit setup at the scheme level. Kindly check and revise.");
            }
        }*/

        CorpMemberBenefit benefit = builder.build();

        return ResultFactory.getSuccessResult(benefit,"Member benefit has been updated.");
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<CorpMemberBenefit> delete(Long idCorpMemberBenefit,
                                            /*Long idMember,
                                            Long idCorpAnniv,
                                            Long idCorpBenefit,*/
                                            String actionUsername) {

        /*if(idMember==null||idMember<1){
            return ResultFactory.getFailResult("Invalid member ID supplied. Member benefit deletion failed.");
        }

        if(idCorpAnniv==null||idCorpAnniv<1){
            return ResultFactory.getFailResult("Invalid corporate anniversary ID supplied. Member benefit deletion failed.");
        }

        if(idCorpBenefit==null||idCorpBenefit<1){
            return ResultFactory.getFailResult("Invalid corporate benefit ID supplied. Member benefit deletion failed.");
        }

        Member member = memberRepo.findOne(idMember);
        if(member==null){
            return ResultFactory.getFailResult("No member with ID [" +idMember+ "] was found. Member benefit deletion failed.");
        }
        CorpAnniv anniv = corpAnnivRepo.findOne(idCorpAnniv);
        if(idCorpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found. Member benefit deletion failed.");
        }
        //MemberAnnivId memberAnnivId = new MemberAnnivId(member,anniv);

        MemberAnniversary memberAnniv = memberAnniversaryRepo.findByCorpAnnivAndMember(anniv,member);
        if(memberAnniv==null){
            return ResultFactory.getFailResult("Please define a cover period (anniversary) for the member first.");
        }
        CorpBenefit corpBenefit = corpBenefitRepo.findOne(idCorpBenefit);
        if(corpBenefit==null){
            return ResultFactory.getFailResult("No corporate benefit with ID ["+idCorpBenefit+"] was found. Member benefit deletion failed.");
        }*/

        //CorpMemberBenefitId benefitId = new CorpMemberBenefitId(corpBenefit,memberAnniv);

        CorpMemberBenefit benefit = repo.findOne(idCorpMemberBenefit);

        if(benefit==null){
            return ResultFactory.getFailResult("No member benefit with parameters supplied exists. Cannot delete.");
        }

        if(billRepo.countByCorpMemberBenefit(benefit)>0){
            return ResultFactory.getFailResult("Benefit has child records (bills). Deleting would create orphan records.");
        }
        if(preAuthRepo.countByCorpMemberBenefit(benefit)>0){
            return ResultFactory.getFailResult("Benefit has child records (LOUs). Deleting would create orphan records.");
        }
        if(premiumInvoiceItemRepo.countByCorpMemberBenefit(benefit)>0){
            return ResultFactory.getFailResult("Benefit has child records (premium invoice items). Deleting would create orphan records.");
        }

        repo.delete(benefit);
        String msg = "Member benefit was removed from the system by " + actionUsername;
        logger.info(msg);

        return ResultFactory.getSuccessResult(msg);
    }
}
