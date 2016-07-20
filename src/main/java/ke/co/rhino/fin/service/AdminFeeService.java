package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.AdminFee;
import ke.co.rhino.fin.entity.AdminFeeType;
import ke.co.rhino.fin.entity.FundInvoice;
import ke.co.rhino.fin.repo.AdminFeeRepo;
import ke.co.rhino.fin.repo.FundInvoiceRepo;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.repo.CorpBenefitRepo;
import ke.co.rhino.uw.repo.CorpMemberBenefitRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Created by akipkoech on 19/07/2016.
 */
@Service("adminFeeService")
@Transactional
public class AdminFeeService implements IAdminFeeService {

    @Autowired
    private AdminFeeRepo repo;
    @Autowired
    private FundInvoiceRepo fundInvoiceRepo;
    @Autowired
    private CorpBenefitRepo corpBenefitRepo;
    @Autowired
    private CorpMemberBenefitRepo corpMemberBenefitRepo;

    @Override
    public Result<AdminFee> create(Long idFundInvoice, BigDecimal amount, String notes,String actionUsername) {

        if(idFundInvoice==null||idFundInvoice<1){
            return ResultFactory.getFailResult("Invalid fund invoice ID provided. Cannot save.");
        }
        Optional<FundInvoice> fundInvoiceOptional = fundInvoiceRepo.getOne(idFundInvoice);
        if(!fundInvoiceOptional.isPresent()){
            return ResultFactory.getFailResult("No fund invoice with ID ["+idFundInvoice+"] was found.");
        }
        AdminFee.AdminFeeBuilder builder = new AdminFee.AdminFeeBuilder(fundInvoiceOptional.get());
        switch (fundInvoiceOptional.get().getAdminFeeType()){
            case PERCENTAGE_OF_FUND:
                // Get percentage from fund invoice
                Double percentage = fundInvoiceOptional.get().getAdminFeePercent();
                BigDecimal bigPercentage = BigDecimal.valueOf(percentage);
                BigDecimal amt = fundInvoiceOptional.get().getAmount().multiply(bigPercentage).divide(BigDecimal.valueOf(100));
                if(amount.compareTo(amt)<0){ //if fee from method parameter is less than the calculated, use calculated figure.
                    amount = amt;
                }
                break;
            case BY_POPULATION:
                // Get count of corpMemberBenefit under the corresponding CorpBenefit;
                CorpBenefit benefit = fundInvoiceOptional.get().getBenefit();
                long population = corpMemberBenefitRepo.countByBenefit(benefit);
                BigDecimal total = fundInvoiceOptional.get().getRatePerHead().multiply(BigDecimal.valueOf(population));
                if(amount.compareTo(total)<0){
                    amount = total;
                }
                break;
            case FLAT_RATE:
                // Just save the amount
                BigDecimal flatRate = fundInvoiceOptional.get().getFlatRateAmount();
                if(amount.compareTo(flatRate)<0){
                    amount = flatRate;
                }
                break;
            case PER_VISIT:
                // Get definition of per visit fee from fund invoice;
                BigDecimal perVisitAmount = fundInvoiceOptional.get().getPerVisitAmount();
                if(amount.compareTo(perVisitAmount)<0){
                    amount = perVisitAmount;
                }
                break;
            default:
                break;

        }

        builder.amount(amount);
        if(!notes.trim().isEmpty()){
            builder.notes(notes);
        }
        AdminFee adminFee = builder.build();
        repo.save(adminFee);
        return ResultFactory.getSuccessResult(adminFee);
    }

    @Override
    public Result<Page<AdminFee>> findAll(Long idCorpBenefit,int pageNo, int size, String actionUsername) {

        if(idCorpBenefit==null||idCorpBenefit<0){
            return ResultFactory.getFailResult("Invalid corporate benefit ID provided.");
        }
        Optional<CorpBenefit> benefitOpt = corpBenefitRepo.getOne(idCorpBenefit);

        PageRequest request = new PageRequest(pageNo-1,size);
        Page<AdminFee> adminFeePage = repo.findByFundInvoice_Benefit(benefitOpt.get(),request);

        return ResultFactory.getSuccessResult(adminFeePage);
    }

    @Override
    public Result<AdminFee> update(Long idAdminFee, Long idFundInvoice, BigDecimal amount,String notes, String actionUsername) {
        return null;
    }

    @Override
    public Result<AdminFee> remove(Long idAdminFee, String actionUsername) {
        // Need to see that no orphan records are created by this action: AdminFeeReceipts
        return null;
    }
}
