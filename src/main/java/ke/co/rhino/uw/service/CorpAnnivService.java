package ke.co.rhino.uw.service;

import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.repo.CategoryRepo;
import ke.co.rhino.uw.repo.CorpAnnivRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.repo.IntermediaryRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 03/02/2016.
 */
@Service("corpAnnivService")
@Transactional
public class CorpAnnivService extends AbstractService implements ICorpAnnivService {

    @Autowired
    protected CorporateRepo corporateRepo;
    @Autowired
    protected CorpAnnivRepo corpAnnivRepo;
    @Autowired
    protected IntermediaryRepo intermediaryRepo;
    @Autowired
    protected CategoryRepo categoryRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<CorpAnniv> create(Long idCorporate,
                                   //Long idCorpAnniv,
                                   Long idIntermediary,
                                   Integer anniv, //TODO verify if indeed we need this field or not
                                   LocalDate inception,
                                   LocalDate expiry,
                                   LocalDate renewalDate,
                                   String actionUsername) {

        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate == null){
            return ResultFactory.getFailResult("Cannot add a cover period without a scheme.");
        }

        if(inception == null){
            return ResultFactory.getFailResult("Cannot add a cover period without a valid inception date.");
        } else {
            if(expiry == null){
                return ResultFactory.getFailResult("Cannot add a cover period without a valid expiry date.");
            } else {
                if(expiry.isBefore(inception)){
                    return ResultFactory.getFailResult("Expiry date cannot predate inception date.");
                }
                //if(Period.between(startDate,endDate).getDays()>)
            }
        }

        if(inception.plusYears(1).isBefore(expiry)){
            return ResultFactory.getFailResult("Policy cannot run for more than one year.");
        }

        if(renewalDate.isBefore(expiry)){
            return ResultFactory.getFailResult("Policy renewal date cannot come before expiry date.");
        }

        /**
         * Before adding a new anniversary, need to ascertain it doesn't exist.
         */
        CorpAnniv testCorpAnniv = corpAnnivRepo.findByCorporateAndAnniv(corporateRepo.findOne(idCorporate),anniv);

        if(testCorpAnniv != null){
            return ResultFactory.getFailResult("Unable to add new cover period: [" + anniv + "]. The anniversary already exists");
        }

        CorpAnniv corpAnniv = new CorpAnniv();
        //corpAnniv.setAnniv(anniv);
        Integer generatedAnniv = generateAnniv(corporate);
        if(anniv != generatedAnniv){
            return ResultFactory.getFailResult("Supplied anniversary does not match the internally generated value.");
        }
        corpAnniv.setAnniv(generatedAnniv);
        corpAnniv.setCorporate(corporateRepo.findOne(idCorporate));

        corpAnniv.setInception(inception);
        corpAnniv.setExpiry(expiry);
        corpAnniv.setRenewalDate(expiry.plusDays(1));
        corpAnniv.setIntermediary(intermediaryRepo.findOne(idIntermediary));

        corpAnnivRepo.save(corpAnniv);

        return ResultFactory.getSuccessResult(corpAnniv);
    }

    @Override@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<CorpAnniv> update(Long idCorporate,
                                    Long idCorpAnniv,
                                    Long idIntermediary,
                                    Integer anniv,
                                    LocalDate inception,
                                    LocalDate expiry,
                                    LocalDate renewalDate,
                                    String actionUsername) {

        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate==null){
            return ResultFactory.getFailResult("Cannot modify a cover period without a scheme.");
        }

        if(inception==null){
            return ResultFactory.getFailResult("Cannot modify a cover period without a valid inception date.");
        } else {
            if(expiry==null){
                return ResultFactory.getFailResult("Cannot modify a cover period without a valid expiry date.");
            } else {
                if(expiry.isBefore(inception)){
                    return ResultFactory.getFailResult("Expiry date cannot predate inception date.");
                }
            }
        }

        if(inception.plusYears(1).isBefore(expiry)){
            return ResultFactory.getFailResult("Policy cannot run for more than one year.");
        }

        if(renewalDate.isBefore(expiry)){
            return ResultFactory.getFailResult("Policy renewal date cannot come before expiry date.");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv); // Retrieve from the database based on idCorpAnniv

        if(corpAnniv == null){
            return ResultFactory.getFailResult("Cannot update the cover period: Anniversary given doesn't exist!");
        }

        CorpAnniv testCorpAnniv = corpAnnivRepo.findByCorporateAndAnniv(corporateRepo.findOne(idCorporate),anniv);

        /**
         * We only allow changing of the following attributes:
         * <ol>
         *     <li>Inception</li>
         *     <li>Expiry</li>
         *     <li>RenewalDate</li>
         *     <li>Intermediary</li>
         * </ol>
         */
        if(testCorpAnniv == null){
            //corpAnniv.setAnniv(anniv);
            //corpAnniv.setCorporate(corporateRepo.findOne(idCorporate));
            return ResultFactory.getFailResult("There is no anniversary corresponding to the given corporate.");
        }

        if(!testCorpAnniv.equals(corpAnniv)){
            return ResultFactory.getFailResult("The anniversary details, anniv: [" + testCorpAnniv.getAnniv() +"], corporate ID: [" + testCorpAnniv.getCorporate().getId() + "] given do not correspond to the ID supplied: [" + corpAnniv.getId() +"]");
        }


        corpAnniv.setInception(inception);
        corpAnniv.setExpiry(expiry);
        corpAnniv.setRenewalDate(expiry.plusDays(1));
        corpAnniv.setIntermediary(intermediaryRepo.findOne(idIntermediary));

        corpAnnivRepo.save(corpAnniv);

        return ResultFactory.getSuccessResult(corpAnniv);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<CorpAnniv> remove(Long idCorpAnniv, String actionUsername) {

        if(idCorpAnniv == null ){
            return ResultFactory.getFailResult("Unable to remove null cover period");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);
        long categoryCount = categoryRepo.countByCorpAnniv(corpAnniv);

        if(corpAnniv==null){
            return ResultFactory.getFailResult("Unable to obtain cover period by ID [ " + idCorpAnniv + " ] for deletion");
        } else if(categoryCount>0){
            return ResultFactory.getFailResult("Cover period has categories defined. Cannot delete.");
        } else {

            corpAnnivRepo.delete(corpAnniv);
            String msg = "Cover period " + corpAnniv.getAnniv() + " was deleted by " + actionUsername;
            logger.info(msg);
            return ResultFactory.getSuccessResult(msg);
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<CorpAnniv> find(Long idCorpAnniv, String actionUsername) {
            CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);
            return ResultFactory.getSuccessResult(corpAnniv);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<CorpAnniv>> findAll(String actionUsername) {
            return ResultFactory.getSuccessResult(corpAnnivRepo.findAll());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<CorpAnniv>> findByCorporate(Long idCorporate, String actionUsername) {
        List<CorpAnniv> corpAnnivList = corpAnnivRepo.findByCorporate(corporateRepo.findOne(idCorporate));
        return ResultFactory.getSuccessResult(corpAnnivList);
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    private Integer generateAnniv(Corporate corporate){
        Integer anniv = corpAnnivRepo.getMax(corporate);
        return anniv == null ? 1 : anniv++;
    }

}
