package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Intermediary;
import ke.co.rhino.uw.entity.IntermediaryType;
import ke.co.rhino.uw.entity.Regulation;
import ke.co.rhino.uw.repo.CorpAnnivRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.repo.IntermediaryRepo;
import ke.co.rhino.uw.repo.RegulationRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
                whTaxRate = 0;
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

            Intermediary intermediary = intermediaryRepo.findByCorpAnniv(anniv);

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
        return null;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Regulation> findOne(Long idRegulation, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Regulation> findByCorpAnniv(Long idCorpAnniv, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<Regulation>> findByCorporate(Long idCorporate, int page, int size, String actionUsername) {
        return null;
    }
}
