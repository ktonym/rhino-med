package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.AdminFeeType;
import ke.co.rhino.fin.entity.FundInvoice;
import ke.co.rhino.fin.repo.AdminFeeRepo;
import ke.co.rhino.fin.repo.FundInvoiceRepo;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.repo.CorpBenefitRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by akipkoech on 18/07/2016.
 */
@Service("fundInvoiceService")
@Transactional
public class FundInvoiceService implements IFundInvoiceService {

    @Autowired
    private FundInvoiceRepo repo;
    @Autowired
    private CorpBenefitRepo corpBenefitRepo;
    @Autowired
    private CorporateRepo corporateRepo;
    @Autowired
    private AdminFeeRepo adminFeeRepo;

    @Override@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<FundInvoice> create(BigDecimal amount, LocalDate invoiceDate, AdminFeeType adminFeeType,
                                      Double adminFeePercent,BigDecimal perVisitAmount,BigDecimal flatRateAmount,
                                      BigDecimal ratePerHead,Long idCorpBenefit, String actionUsername) {

        if(amount==null||amount.compareTo(BigDecimal.ZERO)<0){
            return ResultFactory.getFailResult("Invalid fund amount. Kindly provide a positive, non-null figure.");
        }

        if(invoiceDate==null||invoiceDate.isAfter(LocalDate.now())){
            invoiceDate = LocalDate.now();
        }

        if(idCorpBenefit==null||idCorpBenefit<1){
            return ResultFactory.getFailResult("Invalid corporate benefit ID provided.");
        }

        CorpBenefit benefit = corpBenefitRepo.findOne(idCorpBenefit);
        if(benefit==null){
            return ResultFactory.getFailResult("No corporate benefit with ID ["+idCorpBenefit+"] was found. Saving failed.");
        }

        if(repo.countByBenefit(benefit)>0){
            return ResultFactory.getFailResult("Corporate benefit has already been invoiced for fund. Cannot save.");
        }

        if(adminFeeType==null){
            return ResultFactory.getFailResult("Administration fee type must be specified. Cannot save.");
        }

        FundInvoice.FundInvoiceBuilder builder = new FundInvoice.FundInvoiceBuilder(benefit,amount,invoiceDate);

        switch (adminFeeType){
            case PERCENTAGE_OF_FUND:
                if(adminFeePercent<=0){
                    return ResultFactory.getFailResult("Provide a percentage for Admin Fee before saving.");
                }
                // set perVisitAmount to null, flat_rate
                perVisitAmount = BigDecimal.ZERO;
                flatRateAmount = BigDecimal.ZERO;
                ratePerHead = BigDecimal.ZERO;
                builder.adminFeePercent(adminFeePercent);
                break;
            case PER_VISIT:
                if(perVisitAmount==null||perVisitAmount.compareTo(BigDecimal.ZERO)<0){
                    return ResultFactory.getFailResult("Provide a valid, non-null administration fee per visit. Cannot save.");
                }
                adminFeePercent = 0.00;
                flatRateAmount = BigDecimal.ZERO;
                ratePerHead = BigDecimal.ZERO;
                builder.perVisitAmount(perVisitAmount);
                break;
            case FLAT_RATE:
                if(flatRateAmount==null||flatRateAmount.compareTo(BigDecimal.ZERO)<0){
                    return ResultFactory.getFailResult("Provide a flat rate administration fee. Cannot save.");
                }
                adminFeePercent = 0.00;
                perVisitAmount = BigDecimal.ZERO;
                ratePerHead = BigDecimal.ZERO;
                builder.flatRateAmount(flatRateAmount);
                break;
            case BY_POPULATION:
                if(ratePerHead==null||ratePerHead.compareTo(BigDecimal.ZERO)<0){
                    return ResultFactory.getFailResult("Provide a rate per head administration fee. Cannot save.");
                }
                adminFeePercent = 0.00;
                perVisitAmount = BigDecimal.ZERO;
                flatRateAmount = BigDecimal.ZERO;
                break;
            default:
                // Maybe same case as BY_POPULATION??
                break;
        }

        builder.adminFeeType(adminFeeType);

        FundInvoice fundInvoice = builder.build();

        repo.save(fundInvoice);

        return ResultFactory.getSuccessResult(fundInvoice);
    }

    @Override@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<FundInvoice> update(Long idFundInvoice,String invoiceNumber,BigDecimal amount, LocalDate invoiceDate,
                                      AdminFeeType adminFeeType,Double adminFeePercent,BigDecimal perVisitAmount,
                                      BigDecimal flatRateAmount, BigDecimal ratePerHead,
                                      Long idCorpBenefit, String actionUsername) {

        if(idFundInvoice==null||idFundInvoice<1){
            return ResultFactory.getFailResult("Invalid fund invoice ID provided. Update failed.");
        }

        if(idCorpBenefit==null||idCorpBenefit<1){
            return ResultFactory.getFailResult("Invalid corporate benefit ID provided. Update failed.");
        }

        if(amount==null||amount.compareTo(BigDecimal.ZERO)<0){
            return ResultFactory.getFailResult("Invalid fund amount. Kindly provide a positive, non-null figure.");
        }

        if(invoiceDate==null||invoiceDate.isAfter(LocalDate.now())){
            invoiceDate = LocalDate.now();
        }

        FundInvoice testFundInvoice = repo.findOne(idFundInvoice);
        if(testFundInvoice==null){
            return ResultFactory.getFailResult("No fund invoice with ID ["+idFundInvoice+"] was found. Update failed.");
        }
        if(!testFundInvoice.getInvoiceNumber().equals(invoiceNumber)){
            return ResultFactory.getFailResult("The fund invoice number belongs to a different ID than the one provided. Update failed.");
        }
        CorpBenefit benefit = corpBenefitRepo.findOne(idCorpBenefit);
        if(benefit==null){
            return ResultFactory.getFailResult("No corporate benefit with ID ["+idCorpBenefit+"] was found. Update failed.");
        }

        FundInvoice.FundInvoiceBuilder builder = new FundInvoice.FundInvoiceBuilder(benefit,amount,invoiceDate)
                .idFundInvoice(idFundInvoice).invoiceNumber(invoiceNumber);

        switch (adminFeeType){
            case PERCENTAGE_OF_FUND:
                if(adminFeePercent<=0){
                    return ResultFactory.getFailResult("Provide a percentage for Admin Fee before saving.");
                }
                // set perVisitAmount to null, flat_rate
                perVisitAmount = BigDecimal.ZERO;
                flatRateAmount = BigDecimal.ZERO;
                ratePerHead = BigDecimal.ZERO;
                builder.adminFeePercent(adminFeePercent);
                break;
            case PER_VISIT:
                if(perVisitAmount==null||perVisitAmount.compareTo(BigDecimal.ZERO)<0){
                    return ResultFactory.getFailResult("Provide a valid, non-null administration fee per visit. Cannot save.");
                }
                adminFeePercent = 0.00;
                flatRateAmount = BigDecimal.ZERO;
                ratePerHead = BigDecimal.ZERO;
                builder.perVisitAmount(perVisitAmount);
                break;
            case FLAT_RATE:
                if(flatRateAmount==null||flatRateAmount.compareTo(BigDecimal.ZERO)<0){
                    return ResultFactory.getFailResult("Provide a flat rate administration fee. Cannot save.");
                }
                adminFeePercent = 0.00;
                perVisitAmount = BigDecimal.ZERO;
                ratePerHead = BigDecimal.ZERO;
                builder.flatRateAmount(flatRateAmount);
                break;
            case BY_POPULATION:
                if(ratePerHead==null||ratePerHead.compareTo(BigDecimal.ZERO)<0){
                    return ResultFactory.getFailResult("Provide a rate per head administration fee. Cannot save.");
                }
                adminFeePercent = 0.00;
                perVisitAmount = BigDecimal.ZERO;
                flatRateAmount = BigDecimal.ZERO;
                break;
            default:
                // Maybe same case as BY_POPULATION??
                break;
        }

        builder.adminFeeType(adminFeeType);

        FundInvoice invoice = builder.build();

        repo.save(invoice);

        return ResultFactory.getSuccessResult(invoice);
    }

    @Override@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<FundInvoice> remove(Long idFundInvoice, String actionUsername) {

        if(idFundInvoice==null||idFundInvoice<0){
            return ResultFactory.getFailResult("Invalid fund invoice ID provided. Cannot delete.");
        }

        Optional<FundInvoice> invoiceOptional = repo.getOne(idFundInvoice);

        if(!invoiceOptional.isPresent()){
            return ResultFactory.getFailResult("No fund invoice with ID ["+idFundInvoice+"] was found. Cannot delete.");
        }

        if(adminFeeRepo.countByFundInvoice(invoiceOptional.get())>0){
            return ResultFactory.getFailResult("Fund invoice has child records(Admin Fee). Cannot delete.");
        }
        String msg = invoiceOptional.get().toString().concat(" was deleted by ").concat(actionUsername);
        repo.delete(idFundInvoice);
        return ResultFactory.getSuccessResult(msg);
    }

    @Override@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<FundInvoice>> findAll(int pageNum, int size, Long idCorporate, String actionUsername) {

        if(idCorporate==null||idCorporate<1){
            return ResultFactory.getFailResult("Invalid corporate ID provided.");
        }
        Corporate corporate = corporateRepo.findOne(idCorporate);
        if(corporate==null){
            return ResultFactory.getFailResult("No corporate with ID ["+idCorporate+"] was found.");
        }
        PageRequest request = new PageRequest(pageNum-1,size);

        Page<FundInvoice> fundInvoicePage = repo.findByBenefit_Category_CorpAnniv_Corporate(corporate,request);
        return ResultFactory.getSuccessResult(fundInvoicePage);

    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    private String generateInvoiceNo(){

        long newNum = repo.count() + 1;
        StringBuilder builder = new StringBuilder("FND-").append(newNum);
        return builder.toString();
    }
}
