package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.GroupRate;
import ke.co.rhino.uw.entity.Quotation;
import ke.co.rhino.uw.entity.QuotationItem;
import ke.co.rhino.uw.repo.GroupRateRepo;
import ke.co.rhino.uw.repo.QuotationItemRepo;
import ke.co.rhino.uw.repo.QuotationRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by user on 03/04/2017.
 */
@Service("quotationItemService")
@Transactional
public class QuotationItemService implements IQuotationItemService {

    @Autowired
    private QuotationItemRepo repo;
    @Autowired
    private QuotationRepo quotationRepo;
    @Autowired
    private GroupRateRepo rateRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<QuotationItem> create(Long idQuotation, Long idPremiumRate,
                                        LocalDate quoteItemDate, Long discountLoadFactor, /*BigDecimal unitPrice,*/
                                        Integer quantity, String actionUsername) {

        if(idQuotation==null||idQuotation<1L){
            return ResultFactory.getFailResult("Invalid or missing quotation ID. Cannot save quotation item");
        }

        if(idPremiumRate==null||idPremiumRate<1L){
            return ResultFactory.getFailResult("Invalid or missing premium rate ID. Cannot save quotation item");
        }

        if(quoteItemDate==null){ //if date is missing, use current date instead
            quoteItemDate = LocalDate.now();
        }

        if(quantity==null||quantity<1){
            return ResultFactory.getFailResult("Number of covered families is missing. Cannot save quotation item");
        }

        if(discountLoadFactor==null||discountLoadFactor==0L){
            discountLoadFactor = 1L;
        }

        GroupRate gRate = rateRepo.findOne(idPremiumRate);

        if(gRate==null){
            return ResultFactory.getFailResult("Invalid premium rate provided. Cannot save quotation item");
        }

        Quotation quote = quotationRepo.findOne(idQuotation);

        if(quote==null){
            return ResultFactory.getFailResult("No quotation with ID ["+idQuotation+"] was found. Cannot save quotation item");
        }

        QuotationItem item = new QuotationItem.QuotationItemBuilder(quote)
                                              .quoteItemDate(quoteItemDate)
                                              .loadDiscountFactor(discountLoadFactor)
                                              .premiumRate(gRate)
                                              .quantity(quantity).build();

        return ResultFactory.getSuccessResult(item);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<QuotationItem> update(Long idQuote, Long idPremiumRate, LocalDate quoteItemDate, Long discountLoadFactor,
                                        /*BigDecimal unitPrice,*/Integer quantity, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<QuotationItem> delete(Long idQuote, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<QuotationItem>> findAll(Long idQuotation, int page, int size, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<QuotationItem>> findAll(Long idQuotation, String actionUsername) {
        if(idQuotation==null||idQuotation<1L){
            return ResultFactory.getFailResult("Invalid quotation ID provided.");
        }

        Quotation quotation = quotationRepo.findOne(idQuotation);

        if(quotation==null){
            return ResultFactory.getFailResult("No quotation with ID ["+idQuotation+"] was found.");
        }

        List<QuotationItem> items = repo.findByQuotation(quotation);

        return ResultFactory.getSuccessResult(items);
    }

}
