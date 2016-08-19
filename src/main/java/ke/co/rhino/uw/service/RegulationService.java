package ke.co.rhino.uw.service;

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

import java.util.Optional;

import static ke.co.rhino.uw.entity.IntermediaryType.BROKER;

/**
 * Created by user on 15-Aug-16.
 */
@Service("regulationService")
@Transactional
public class RegulationService implements IRegulationService {

    @Autowired
    private RegulationRepo repo;
    @Autowired
    private CorpAnnivRepo corpAnnivRepo;
    @Autowired
    private CorporateRepo corporateRepo;
    @Autowired
    private IntermediaryRepo intermediaryRepo;
    @Autowired
    private CoPayRepo coPayRepo;

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<Regulation> create(Long idCorpAnniv, Boolean coPay, String actionUsername) {

        if(idCorpAnniv==null||idCorpAnniv<1){
            return ResultFactory.getFailResult("Invalid corporate anniversary id provided.");
        }

        CorpAnniv anniv = corpAnnivRepo.getOne(idCorpAnniv);

        if(anniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found.");
        }

        Intermediary intermediary = anniv.getIntermediary();

        IntermediaryType intermediaryType = intermediary.getType();

        Integer whTaxRate = 0;
        Integer commRate = 0;

        switch (intermediaryType){
            case BROKER:
                whTaxRate = 5;
                commRate = 10;
                break;
            case AGENCY:
                whTaxRate = 10;
                commRate = 10;
                break;
            case AGENT:
                whTaxRate = 10;
                commRate = 10;
                break;
            default:
                break;
                //whTaxRate = 0;
        }

        if(coPay==null){
            coPay = false;
        }

        Regulation regulation = new Regulation.RegulationBuilder(anniv)
                                              .commRate(commRate)
                                              .coPay(coPay)
                                              .whTaxRate(whTaxRate).build();

        repo.save(regulation);

        return ResultFactory.getSuccessResult(regulation);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<Regulation> update(Long idRegulation, Long idCorpAnniv,
                                     Integer commRate, Integer whTaxRate, Boolean coPay, String actionUsername) {

        if(idRegulation == null || idRegulation<1 || idCorpAnniv == null || idCorpAnniv<1){
            return ResultFactory.getFailResult("Invalid regulation ID specified. Update failed.");
        }

        Optional<Regulation> regulationOpt = repo.getOne(idRegulation);

        if(regulationOpt.isPresent()){
            CorpAnniv anniv = corpAnnivRepo.getOne(idCorpAnniv);
            if(anniv==null){
                return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found. Update failed.");
            }

            Regulation.RegulationBuilder builder = new Regulation.RegulationBuilder(anniv)
                    .idRegulation(idRegulation);

            if(coPay == null){
                builder.coPay(false);
            } else {
                builder.coPay(coPay);
            }

            // commission rate and withholding tax rate ought to be established from corpAnniv.getIntermediary().getType

            Intermediary intermediary = intermediaryRepo.findByAnniv(idCorpAnniv);

            switch (intermediary.getType()){
                case DIRECT:
                    builder.commRate(0);
                    builder.whTaxRate(0);
                    break;
                case BROKER:
                    builder.commRate(10);
                    builder.whTaxRate(5);
                    break;
                default:
                    builder.commRate(10);
                    builder.whTaxRate(10);
                    break;
            }

            Regulation regulation = builder.build();

            return ResultFactory.getSuccessResult(regulation);

        } else {
            return ResultFactory.getFailResult("No regulation with ID ["+idRegulation+"] was found. Update failed.");
        }
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<Regulation> delete(Long idRegulation, String actionUsername) {

        if(idRegulation == null || idRegulation<1){
            return ResultFactory.getFailResult("Invalid regulation ID provided. Cannot delete.");
        }

        Optional<Regulation> regulationOpt = repo.getOne(idRegulation);

        if(regulationOpt.isPresent()){

            if(coPayRepo.countByRegulation(regulationOpt.get())>0){
                return ResultFactory.getFailResult("The regulation has child records(CoPay) associated with it. Kindly remove them first. Delete failed.");
            }

            repo.delete(idRegulation);

            String msg = "Regulation with ID ["+idRegulation+"] deleted by "+actionUsername;

            return ResultFactory.getSuccessResult(regulationOpt.get(),msg);

        } else {
            return ResultFactory.getFailResult("No regulation with ID ["+idRegulation+"] was found. Delete failed.");
        }


    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Regulation> findOne(Long idRegulation, String actionUsername) {

        if(idRegulation==null||idRegulation<1){
            return ResultFactory.getFailResult("Invalid regulation ID provided.");
        }

        Optional<Regulation> regulationOpt = repo.getOne(idRegulation);

        if(regulationOpt.isPresent()){
            return ResultFactory.getSuccessResult(regulationOpt.get());
        }

        return ResultFactory.getFailResult("No regulation with ID ["+idRegulation+"] was found.");
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Regulation> findByCorpAnniv(Long idCorpAnniv, String actionUsername) {

        if(idCorpAnniv==null||idCorpAnniv<1){
            return ResultFactory.getFailResult("Invalid corporate anniversary ID provided.");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);

        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found.");
        }

        Optional<Regulation> regOpt = repo.findByCorpAnniv(corpAnniv);

        if(regOpt.isPresent()){
            return ResultFactory.getSuccessResult(regOpt.get());
        } else {
            return ResultFactory.getFailResult("No regulation has been defined for the anniversary provided.");
        }

    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<Regulation>> findByCorporate(Long idCorporate, int page, int size, String actionUsername) {

        if(idCorporate==null||idCorporate<1){
            return ResultFactory.getFailResult("Invalid corporate anniversary provided.");
        }

        Optional<Corporate> corpOpt = corporateRepo.getOne(idCorporate);

        if(corpOpt.isPresent()){

            PageRequest request = new PageRequest(page,size);
            Page<Regulation> regulationPage = repo.findByCorpAnniv_Corporate(corpOpt.get(),request);
            return ResultFactory.getSuccessResult(regulationPage);

        } else {

            return ResultFactory.getFailResult("No regulation(s) found for corporate provided.");

        }


    }
}
