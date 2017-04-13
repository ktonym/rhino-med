package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.Quotation;
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
import java.util.List;

/**
 * Created by user on 29/03/2017.
 */
@Service("quotationService")
@Transactional
public class QuotationService implements IQuotationService {

    @Autowired
    private QuotationRepo repo;
    @Autowired
    private QuotationItemRepo quotationItemRepo;
    @Autowired
    private CorporateRepo corporateRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Quotation> create(LocalDate quotationDate, Long idCorporate, String actionUsername) {

        if(quotationDate==null){
            return ResultFactory.getFailResult("Quotation date is missing. Creation failed.");
        }

        if(idCorporate==null||idCorporate<1L){
            return ResultFactory.getFailResult("Cannot create quotation without a scheme.");
        }

        Corporate corp = corporateRepo.findOne(idCorporate);

        if(corp==null){
            return ResultFactory.getFailResult("Cannot create quotation without a valid scheme. Supplied ID ["+idCorporate+"] non-existent.");
        }

        Quotation quote = new Quotation.QuotationBuilder(corp).quotationDate(quotationDate).build();

        repo.save(quote);

        return ResultFactory.getSuccessResult(quote);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Quotation> delete(Long idQuotation, String actionUsername) {

        if(idQuotation==null||idQuotation<1L){
            return ResultFactory.getFailResult("Invalid quotation ID provided. Cannot delete.");
        }

        Quotation quote = repo.findOne(idQuotation);

        if(quote==null){
            return ResultFactory.getFailResult("Cannot delete a quotation without a valid ID. Supplied ID ["+idQuotation+"]");
        }

        long itemCount = quotationItemRepo.countByQuotation(quote);

        if(itemCount>0){
            return ResultFactory.getFailResult("Cannot delete a quotation with quotation items.");
        }

        String msg = "Quotation ID ["+idQuotation+"] was deleted by " + actionUsername;

        repo.delete(quote);

        return ResultFactory.getSuccessResult(msg);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<Quotation>> findByCorporate(Long idCorporate, String actionUsername) {

        if(idCorporate==null||idCorporate<1L){
            return ResultFactory.getFailResult("Invalid corporate ID provided. Quotation search failed.");
        }

        Corporate corp = corporateRepo.findOne(idCorporate);

        if(corp==null){
            return ResultFactory.getFailResult("No scheme with ID ["+idCorporate+"] was found. Quotation search failed.");
        }

        List<Quotation> quotations = repo.findByCorporate(corp);

        return ResultFactory.getSuccessResult(quotations);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Quotation>> findByDate(LocalDate quotationDate, int page, int size, String actionUsername) {

        if(quotationDate==null){
            return ResultFactory.getFailResult("Please provide a valid date with which to filter.");
        }

        QuotationSpecification spec = new QuotationSpecification(new SearchCriteria("quotationDate",":",quotationDate));
        PageRequest request = new PageRequest(page-1,size);

        Page<Quotation> quotationPage = repo.findAll(spec,request);
        //Page<Quotation> quotations = repo.findByQuotationDate(quotationDate,request);

        return ResultFactory.getSuccessResult(quotationPage);
    }

    @Override
    public Result<Page<Quotation>> findByDateBetween(LocalDate date1, LocalDate date2, int page, int size, String actionUsername) {

        if(date1==null){
            return ResultFactory.getFailResult("Please provide a valid FROM date with which to filter.");
        }

        if(date2==null){
            return ResultFactory.getFailResult("Please provide a valid TO date with which to filter.");
        }

        PageRequest request = new PageRequest(page-1,size);

        /*QuotationSpecification spec1 = new QuotationSpecification(new SearchCriteria("quotationDate",">", date1));

        QuotationSpecification spec2 = new QuotationSpecification(new SearchCriteria("quotationDate", "<=", date2));*/

        QuotationSpecification spec = new QuotationSpecification(new SearchCriteria("quotationDate","btn", date1,date2));

        Page<Quotation> quotations = repo.findAll(spec,request);

        return ResultFactory.getSuccessResult(quotations);
    }

    @Override
    public Result<Quotation> findOne(Long idQuotation, String actionUsername) {

        if(idQuotation==null||idQuotation<1L){
            return ResultFactory.getFailResult("Invalid quotation ID provided.");
        }

        Quotation quotation = repo.findOne(idQuotation);

        if(quotation==null){
            return ResultFactory.getFailResult("No quotation with ID ["+idQuotation+"] was found.");
        }

        return ResultFactory.getSuccessResult(quotation);

    }


}
