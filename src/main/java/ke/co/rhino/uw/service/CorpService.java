package ke.co.rhino.uw.service;

import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.repo.ContactPersonRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 27/01/2016.
 */
@Service("corpService")
@Transactional
public class CorpService extends AbstractService implements ICorpService{

    @Autowired
    private CorporateRepo corpRepo;

    @Autowired
    private ContactPersonRepo contactPersonRepo;

    final protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Corporate>> findAll(int pageNum, int size,String actionUsername){

        PageRequest request = new PageRequest(pageNum-1,size, Sort.Direction.ASC,"name");
        Page<Corporate> corpPage = corpRepo.findAll(request);

        return ResultFactory.getSuccessResult(corpPage);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Corporate> create(String name,String abbreviation, String pin,String tel,String email,
                                    String postalAddress,LocalDate joined,LocalDateTime lastUpdate,
                                    String actionUsername){

        if(corpRepo.findByNameEqualsIgnoreCase(name)!=null){
            return ResultFactory.getFailResult("A corporate with a similar name exists.");
        }

        if(corpRepo.findByAbbrevEqualsIgnoreCase(abbreviation)!=null){
            return ResultFactory.getFailResult("A corporate with a similar abbreviation exists.");
        }

        if(corpRepo.findByPinEqualsIgnoreCase(pin)!=null){
            return ResultFactory.getFailResult("A corporate with a similar PIN exists.");
        }

        Corporate corp = new Corporate.CorporateBuilder(name,abbreviation)
                .email(email)
                .joined(joined)
                .pin(pin)
                .tel(tel)
                .postalAddress(postalAddress)
                .lastUpdate(lastUpdate==null?LocalDateTime.now():lastUpdate)
                .build();
        corpRepo.save(corp);
        return ResultFactory.getSuccessResult(corp);
    }

    //TODO figure out how to avoid using this method!
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<List<Corporate>> create(List<Map<String, Object>> corporateMap, String actionUsername) {

        List<Corporate> corpList = new ArrayList<>();

        for (Map map : corporateMap){

            String name = String.valueOf(map.get("name"));
            String abbrev = String.valueOf(map.get("abbreviation"));
            String pin = String.valueOf(map.get("pin"));
            String email = String.valueOf(map.get("email"));
            String postalAd = String.valueOf(map.get("postalAddress"));
            String tel = String.valueOf(map.get("tel"));
            LocalDate joined = (LocalDate) map.get("joined");
            LocalDateTime lastUpdate = (LocalDateTime) map.get("lastUpdate");

            Corporate corp = new Corporate.CorporateBuilder(name,abbrev)
                    .email(email)
                    .joined(joined)
                    .pin(pin)
                    .postalAddress(postalAd)
                    .tel(tel)
                    .lastUpdate(lastUpdate==null?LocalDateTime.now():lastUpdate)
                    .build();

            corpList.add(corp);
        }

        corpRepo.save(corpList);

        return ResultFactory.getSuccessResult(corpList);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Corporate> find(Long idCorporate, String actionUsername) {
        Corporate corp = corpRepo.findOne(idCorporate);
        return ResultFactory.getSuccessResult(corp);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Corporate> remove(Long idCorporate, String actionUsername) {

        if(idCorporate == null){
            return ResultFactory.getFailResult("Unable to remove Corporate. Null idCorporate!");
        }

        Corporate corporate = corpRepo.findOne(idCorporate);

        if( corporate == null){
            return ResultFactory.getFailResult("Unable to load Corporate with idCorporate=" + idCorporate + " for removal");
        } else {

            if((corporate.getAnnivs()==null || corporate.getAnnivs().isEmpty()) &&
                    (corporate.getContactPersons()==null || corporate.getContactPersons().isEmpty()) &&
                    (corporate.getRates()==null || corporate.getRates().isEmpty())){
                corpRepo.delete(corporate);
                String msg = "Corporate " + corporate.getName()
                        + " was deleted by " + actionUsername;
                logger.info(msg);
                return ResultFactory.getSuccessResult(msg);
            } else {
                return ResultFactory.getFailResult("Corporate has child record(s) and could not be deleted");
            }

        }
    }

    @Override
    public Result<Corporate> update(Long idCorporate, String name,
                                    String abbreviation,String pin, String tel, String email,
                                    String postalAddress, LocalDate joined,
                                    LocalDateTime lastUpdate, String actionUsername) {

        Corporate corporate = corpRepo.findOne(idCorporate);

        Corporate corpByName = corpRepo.findByNameEqualsIgnoreCase(name);
        Corporate corpByAbbrev = corpRepo.findByAbbrevEqualsIgnoreCase(abbreviation);
        Corporate corpByEmail = corpRepo.findByEmailEqualsIgnoreCase(email);
        Corporate corpByPIN = corpRepo.findByPinEqualsIgnoreCase(pin);

        if(corporate == null){
            return ResultFactory.getFailResult("Unable to find scheme with ID = " + idCorporate);
        }

        if (corpByName != null ) {
            if (!corpByName.equals(corporate)) {
                return ResultFactory.getFailResult("A corporate with a similar name exists. Edit failed.");
            }

        }

        if (corpByPIN != null ) {
            if (!corpByPIN.equals(corporate)) {
                return ResultFactory.getFailResult("A corporate with a similar PIN exists. Edit failed.");
            }

        }

        if (corpByAbbrev != null) {
            if (!corpByAbbrev.equals(corporate)) {
                return ResultFactory.getFailResult("A corporate with a similar abbreviation exists. Edit failed.");
            }
        }

        if (corpByEmail != null ) {
            if (!corpByEmail.equals(corporate)) {
                return ResultFactory.getFailResult("A corporate with a similar email address exists. Edit failed.");
            }
        }
        Corporate corp = new Corporate.CorporateBuilder(name,abbreviation)
                .email(email)
                .pin(pin)
                .lastUpdate(lastUpdate).idCorporate(idCorporate)
                .tel(tel).postalAddress(postalAddress).build();

        corpRepo.save(corp);
        return ResultFactory.getSuccessResult(corp);

    }

    @Override
    public Result<List<Corporate>> findModifiedAfter(LocalDateTime time, String actionUsername) {
        List<Corporate> corporateList = corpRepo.findByLastUpdateAfter(time);
        return ResultFactory.getSuccessResult(corporateList);
    }

    @Override
    public Result<List<Corporate>> findAddedAfter(LocalDate time, String actionUsername) {
        List<Corporate> corporateList = corpRepo.findByJoinedAfter(time);
        return ResultFactory.getSuccessResult(corporateList);
    }

    @Override
    public Result<List<Corporate>> findAddedBefore(LocalDate time, String actionUsername) {
        List<Corporate> corporateList = corpRepo.findByJoinedBefore(time);
        return ResultFactory.getSuccessResult(corporateList);
    }
}
