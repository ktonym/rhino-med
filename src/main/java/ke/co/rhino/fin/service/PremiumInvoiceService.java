package ke.co.rhino.fin.service;

import com.sun.org.apache.regexp.internal.RE;
import ke.co.rhino.fin.entity.BusinessClass;
import ke.co.rhino.fin.entity.PremiumInvoice;
import ke.co.rhino.fin.repo.PremiumInvoiceItemRepo;
import ke.co.rhino.fin.repo.PremiumInvoiceRepo;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.repo.CorpAnnivRepo;
import ke.co.rhino.uw.repo.CorpBenefitRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.RasterFormatException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by user on 23-Aug-16.
 */
@Service("premiumInvoiceService")
@Transactional
public class PremiumInvoiceService implements IPremiumInvoiceService {

    @Autowired
    private PremiumInvoiceRepo repo;
    @Autowired
    private CorpBenefitRepo corpBenefitRepo;
    @Autowired
    private CorporateRepo corporateRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<PremiumInvoice> create(BusinessClass businessClass,
                                         LocalDate invoiceDate,
                                         Long idCorpBenefit,
                                         Long idParentPremium,
                                         String actionUsername) {

        if (businessClass==null){
            return ResultFactory.getFailResult("Please indicate whether invoicing for NEW,CANCELLED,ADDITION OR RENEWED business.");
        }

        if(idCorpBenefit==null||idCorpBenefit<1){
            return ResultFactory.getFailResult("Invalid benefit provided. Cannot invoice.");
        }

        Optional<CorpBenefit> corpBenefitOpt = corpBenefitRepo.getOne(idCorpBenefit);

        if(!corpBenefitOpt.isPresent()){
            return ResultFactory.getFailResult("Invalid corporate benefit ID ["+idCorpBenefit+"] provided. Invoicing failed.");
        }

        //Need to check whether it's a main benefit.
        if(!corpBenefitOpt.get().isMainBenefit()){
            return ResultFactory.getFailResult("Invoicing is not allowed for child benefits.");
        }

        if(invoiceDate==null){
            invoiceDate = LocalDate.now();
        }

        String invNo = generateInvoiceNo();
        PremiumInvoice.PremiumInvoiceBuilder builder = new PremiumInvoice.PremiumInvoiceBuilder(invNo,invoiceDate,corpBenefitOpt.get());

        switch (businessClass){
            case NEW:
                builder.stampDuty(40);
                break;
            case RENEWAL:
                builder.stampDuty(0);
                break;
            case ADDITION:
                builder.stampDuty(0);
                break;
            case CANCELLATION:
                // query parent PremiumInvoice, and apply its value of stamp duty
                if(idParentPremium==null||idParentPremium<1){
                    return ResultFactory.getFailResult("No premium invoice for cancelling was provided. Save failed.");
                }
                Optional<PremiumInvoice> parentInvoiceOpt = repo.getOne(idParentPremium);
                if(parentInvoiceOpt.isPresent()){
                    builder.stampDuty(parentInvoiceOpt.get().getStampDuty());
                }else {
                    return ResultFactory.getFailResult("Please indicate a valid premium invoice for reversal. Save failed.");
                }
                break;
            default:
                break;
        }

        PremiumInvoice premiumInvoice = builder.build();
        repo.save(premiumInvoice);
        //TODO initiate a premiumInvoiceItemService.create() call...
        return ResultFactory.getSuccessResult(premiumInvoice);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<PremiumInvoice> update(Long idPremiumInvoice, String invoiceNumber, BusinessClass businessClass, LocalDate invoiceDate, Long idCorpBenefit, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<PremiumInvoice>> findAll(Long idCorporate, int page, int size, String actionUsername) {

        if(idCorporate==null||idCorporate<1){
            return ResultFactory.getFailResult("Invalid corporate provided.");
        }

        Corporate corporate = corporateRepo.findOne(idCorporate);
        if(corporate==null){
            return ResultFactory.getFailResult("No corporate with ID ["+idCorporate+"] was found.");
        }

        PageRequest request = new PageRequest(page-1,size);
        Page<PremiumInvoice> premiumInvoicePage = repo.findAll(request);

        return ResultFactory.getSuccessResult(premiumInvoicePage);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<PremiumInvoice> delete(Long idPremiumInvoice, String actionUsername) {

        if(idPremiumInvoice==null||idPremiumInvoice<1){
            return ResultFactory.getFailResult("Invalid premium invoice ID provided. Delete failed.");
        }
        //Check whether the invoice exists in the system..
        Optional<PremiumInvoice> invoiceOptional = repo.getOne(idPremiumInvoice);
        if(invoiceOptional.isPresent()){
            PremiumInvoice invoice = invoiceOptional.get();
            Optional<PremiumInvoice> reversalInvoiceOpt = repo.findByParentInvoice(invoice);
            if(reversalInvoiceOpt.isPresent()){
                return ResultFactory.getFailResult("The invoice you are trying to delete has been reversed with ["+reversalInvoiceOpt.get().getInvoiceNumber()+"]. Delete failed.");
            } else {
                repo.delete(invoice);
                String msg = "Invoice " + invoice.toString() + " deleted by ".concat(actionUsername);
                return ResultFactory.getSuccessResult(invoice,msg);
            }
        }else {
            return ResultFactory.getFailResult("No premium invoice with ID["+idPremiumInvoice+"] was found. Delete failed.");
        }
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    private String generateInvoiceNo(){

        StringBuilder builder = new StringBuilder("INV-");
        long cnt = repo.count()+1;

        return builder.append(cnt).toString();
    }

}
