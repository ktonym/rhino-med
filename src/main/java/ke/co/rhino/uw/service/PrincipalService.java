package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.Principal;
import ke.co.rhino.uw.repo.*;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.Temporal;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 05/05/2016.
 */
@Service("principalService")
@Transactional
public class PrincipalService implements IPrincipalService {

    @Autowired
    private PrincipalRepo principalRepo;

    @Autowired
    private CorporateRepo corporateRepo;

    @Autowired
    private CategoryPrincipalRepo categoryPrincipalRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private CorpAnnivRepo corpAnnivRepo;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Principal> create(//String familyNo,
                                    String firstName,
                                    String surname,
                                    String otherNames,
                                    LocalDate dob,
                                    Long idCorporate,
                                    String actionUsername) {

        if(firstName.trim().length()==0 || firstName==null || surname.trim().length()==0 || surname==null){
            return ResultFactory.getFailResult("First name and surname are required. Principal creation failed.");
        }

        if(Period.between(dob,LocalDate.now()).getYears()<18){
            return ResultFactory.getFailResult("The principal must be at least 18 years. Principal creation failed.");
        }

        if(idCorporate==null){
            return ResultFactory.getFailResult("Null corporate ID ["+idCorporate+"] supplied. Principal creation failed");
        }
        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate==null){
            return ResultFactory.getFailResult("No corporate with ID ["+idCorporate+"] exists. Principal creation failed.");
        }

        Principal principal = new Principal();

        principal.setCorporate(corporate);
        principal.setDob(dob);
        principal.setFirstName(firstName);
        principal.setSurname(surname);
        if(otherNames!=null && otherNames.trim().length()>0 ) {
            principal.setOtherNames(otherNames);
        }

        principal.setFamilyNo(generateFamilyNo(corporate));

        principalRepo.save(principal);

        return ResultFactory.getSuccessResult(principal);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Principal> update(Long idPrincipal,
                                    String familyNo,
                                    String firstName,
                                    String surname,
                                    String otherNames,
                                    LocalDate dob,
                                    //Long idCorporate,
                                    String actionUsername) {

        if(idPrincipal==null || idPrincipal<1){
            return ResultFactory.getFailResult("Invalid Principal ID [" + idPrincipal + "] supplied. Edit failed.");
        }

//        if(idCorporate==null || idCorporate<1){
//            return ResultFactory.getFailResult("Invalid Corporate ID [" + idCorporate + "] supplied. Edit failed.");
//        }

        if(familyNo.trim().length()<8){
            return ResultFactory.getFailResult("Invalid family number [" + familyNo + "] supplied. Edit failed.");
        }

        if(firstName.trim().length()==0 || firstName.equals("") || surname.trim().length()==0 || surname.equals("")){
            return ResultFactory.getFailResult("Invalid first name or surname. Edit failed");
        }

        if(Period.between(dob,LocalDate.now()).getYears()<18){
            return ResultFactory.getFailResult("The principal must be at least 18 years. Edit failed.");
        }

//        Corporate corporate = corporateRepo.getOne(idCorporate);
//
//        if(corporate == null){
//            return ResultFactory.getFailResult("No corporate with supplied ID exits. Edit failed.");
//        }

        Principal principalFromFamilyNo = principalRepo.findByFamilyNo(familyNo);

        Principal principalFromId = principalRepo.findOne(idPrincipal);

        if(principalFromId==null){
            return ResultFactory.getFailResult("No principal with supplied ID exists. Edit failed.");
        }

        if(principalFromFamilyNo.getId()!=idPrincipal){
            return ResultFactory.getFailResult("Family number supplied [" + familyNo + "] does not match the supplied ID. Edit failed.");
        }

        principalFromId.setFirstName(firstName);

        if(!otherNames.equals("") && otherNames.trim().length() != 0 ) {
            principalFromId.setOtherNames(otherNames);
        }

        principalFromId.setSurname(surname);

        principalRepo.save(principalFromId);

        return ResultFactory.getSuccessResult(principalFromId);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Principal> remove(Long idPrincipal, String actionUsername) {

        Principal principal = principalRepo.findOne(idPrincipal);

        if(principal == null){
            return ResultFactory.getFailResult("Invalid ID["+idPrincipal+"] supplied. Delete failed.");
        }

        long catCount = categoryPrincipalRepo.countByPrincipal(principal);
        if(catCount > 0){
            return ResultFactory.getFailResult("Principal has categories defined. Delete failed.");
        }

        /*long dependantCount = memberRepo.countByPrincipal(principal);

        if(dependantCount > 0) {
            return ResultFactory.getFailResult("Principal has dependants defined. Delete failed.");
        }*/

        principalRepo.delete(principal);

        String msg = principal.toString().concat(" deleted by ").concat(actionUsername);
        logger.info(msg);

        return ResultFactory.getSuccessResult(msg);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Stream<Principal>> findAll(String actionUsername) {
        return ResultFactory.getSuccessResult(principalRepo.findAll().parallelStream());
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Principal>> findByCorporate(Long idCorporate, Integer page, Integer size, String actionUsername) {

        if(idCorporate == null){
            return ResultFactory.getFailResult("Cannot find Principal(s) for a null idCorporate!");
        }

        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate==null){
            return ResultFactory.getFailResult("No corporate with ID ["+idCorporate+"] was found");
        }

        PageRequest request = new PageRequest(page-1,size);

        Page<Principal> principalPage = principalRepo.findByCorporate(corporate, request);

        return ResultFactory.getSuccessResult(principalPage);

    }

    @Override
    public Result<Page<Principal>> findByCorpAnniv(Long idCorpAnniv, Integer pageNo, Integer size, String actionUsername) {

        if(idCorpAnniv == null){
            return ResultFactory.getFailResult("Cannot find Principal(s) for a null corporate anniversary ID");
        }

        CorpAnniv anniv = corpAnnivRepo.findOne(idCorpAnniv);

        if(anniv == null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found in the system.");
        }

        PageRequest request = new PageRequest(pageNo-1,size);

        Page<Principal> principalPage = principalRepo.findByCorpAnniv(idCorpAnniv, request);

        return ResultFactory.getSuccessResult(principalPage);
    }

    private String generateFamilyNo(Corporate corporate){

        String prefix = corporate.getAbbreviation();

        long newNum = principalRepo.countByCorporate(corporate) + 1;
        String newNumStr = String.valueOf(newNum);
        int i = newNumStr.length();
        StringBuilder strBuilder = new StringBuilder(newNumStr);
        if (i<4){
            while(i<4) {
                strBuilder.insert(0,"0");
                i++;
            }
        }

        return strBuilder.insert(0,"/").insert(0,prefix).toString();
    }
}
