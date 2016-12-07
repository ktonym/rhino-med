package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Intermediary;
import ke.co.rhino.uw.entity.IntermediaryType;
import ke.co.rhino.uw.repo.IntermediaryRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by akipkoech on 14/03/2016.
 */
@Service("intermediaryService")
@Transactional
public class IntermediaryService implements IIntermediaryService {

    @Autowired
    private IntermediaryRepo intermediaryRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Intermediary> create(String name,
                                       IntermediaryType type,
                                       String pin,
                                       String email,
                                       String tel,
                                       String town,
                                       String street,
                                       String postalAddress,
                                       LocalDate joinDate,
                                       LocalDateTime lastUpdate,
                                       String username) {


        if(intermediaryRepo.findByNameEqualsIgnoreCase(name)!=null){
            return ResultFactory.getFailResult("An intermediary with a similar name exists in the system.");
        }

        if(intermediaryRepo.findByPinEqualsIgnoreCase(pin)!=null){
            return ResultFactory.getFailResult("An intermediary with a similar PIN exists in the system.");
        }

        Intermediary intermediary = new Intermediary.IntermediaryBuilder(name,pin,type,joinDate)
                .email(email).postalAddress(postalAddress).street(street).tel(tel)
                .town(town).lastUpdate(lastUpdate)
                .build();

        intermediaryRepo.save(intermediary);

        return ResultFactory.getSuccessResult(intermediary);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Intermediary> update(Long idIntermediary,
                                       String name,
                                       IntermediaryType type,
                                       String pin,
                                       String email,
                                       String tel,
                                       String town,
                                       String street,
                                       String postalAddress,
                                       LocalDate joinDate,
                                       LocalDateTime lastUpdate,
                                       String username) {

        Intermediary intermediary = intermediaryRepo.findOne(idIntermediary);

        Intermediary intermedByName = intermediaryRepo.findByNameEqualsIgnoreCase(name);

        Intermediary intermedByPIN = intermediaryRepo.findByPinEqualsIgnoreCase(pin);

        if(intermedByName != null ){
            if(!intermediary.equals(intermedByName)){
                return ResultFactory.getFailResult("The name is in use by a different intermediary.");
            }
        }

        if(intermedByPIN != null){
            if(!intermediary.equals(intermedByPIN)){
                return ResultFactory.getFailResult("The PIN is in use by a different intermediary.");
            }
        }

        Intermediary intermed = new Intermediary.IntermediaryBuilder(name,pin,type,joinDate)
                .email(email).lastUpdate(lastUpdate).postalAddress(postalAddress)
                .street(street).tel(tel).town(town).idIntermediary(idIntermediary)
                .build();

        intermediaryRepo.save(intermed);
        return ResultFactory.getSuccessResult(intermed);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<Intermediary>> findAll(String username) {
        return ResultFactory.getSuccessResult(intermediaryRepo.findAll());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Intermediary> find(Long idIntermediary, String username) {
        return null;
    }

    @Override
    public Result<Intermediary> findByAnniv(Long idCorpAnniv, String username) {
        return ResultFactory.getSuccessResult(intermediaryRepo.findByAnniv(idCorpAnniv));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Intermediary> remove(Long idIntermediary, String username) {
        return null;
    }
}
