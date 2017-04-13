package ke.co.rhino.uw.service;

import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.uw.entity.ContactPerson;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.repo.ContactPersonRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akipkoech on 02/02/2016.
 */
@Service("contactPersonService")
@Transactional
public class ContactPersonService extends AbstractService implements IContactPersonService {
    @Autowired
    protected ContactPersonRepo contactPersonRepo;
    @Autowired
    protected CorporateRepo corporateRepo;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public Result<ContactPerson> store(Long idContactPerson,
                                     Long idCorporate,
                                     String firstName,
                                     String surname,
                                     String jobTitle,
                                     String email,
                                     String tel,
                                     String actionUsername) {

        //User actionUser = userRepo.findOne(actionUsername);

//        if(!isValidUser(actionUsername)){
//            return ResultFactory.getFailResult(USER_INVALID);
//        }

        ContactPerson contactPerson;
        ContactPerson contactPersonByEmail=contactPersonRepo.findByEmail(email);
        /**
         * check if any of <b></b>
         */

        if(idCorporate==null){
            return ResultFactory.getFailResult("Unable to store contact details without a valid corporate");
        }

        if(idContactPerson==null){
            /**
             * A new contact for this corporate
             * Need to set corporate only at creation stage. Otherwise, no changing thereafter
             */

            contactPerson = new ContactPerson();
            contactPerson.setCorporate(corporateRepo.findOne(idCorporate));
            if(contactPersonByEmail==null){
                contactPerson.setEmail(email);
            } else{
                return ResultFactory.getFailResult("Email supplied is already in use");
            }
        } else {
            /**
             * Updating a contact
             * Therefore need to check that <b>email</b> is unique
             */
            //ContactPerson contactPerson = contactPersonBuilder.build();
            contactPerson = contactPersonRepo.findOne(idContactPerson);
            if(!contactPersonByEmail.equals(contactPerson)){
                return ResultFactory.getFailResult("Email is in use by " + contactPersonByEmail.getFirstName().concat(" ").concat(contactPersonByEmail.getSurname()));
            }

        }

        contactPerson.setFirstName(firstName);
        contactPerson.setSurname(surname);
        contactPerson.setJobTitle(jobTitle);
        contactPerson.setTel(tel);
        contactPersonRepo.save(contactPerson);

        return ResultFactory.getSuccessResult(contactPerson);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<ContactPerson> remove(Long idContactPerson, String actionUsername) {

//        if(!isValidUser(actionUsername)){
//            return ResultFactory.getFailResult(USER_INVALID);
//        }

        ContactPerson contactPerson = contactPersonRepo.findOne(idContactPerson);

        if(contactPerson==null){
            return ResultFactory.getFailResult("Unable to obtain contact [ " + idContactPerson + " ] for deletion");
        }

        contactPersonRepo.delete(contactPerson);
        String msg = "Contact " + contactPerson.getFirstName().concat(" ").concat(contactPerson.getSurname()) +" was deleted by " +actionUsername;
        logger.info(msg);
        return ResultFactory.getSuccessResult(msg);

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<ContactPerson>> findAll(Long idCorporate, String actionUsername) {

//        if(!isValidUser(actionUsername)){
//            return ResultFactory.getFailResult(USER_INVALID);
//        }

        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate==null){
            return ResultFactory.getFailResult("Cannot find a corporate with null ID[ " + idCorporate + " ]");
        }

        List<ContactPerson> contactPersonList = contactPersonRepo.findByCorporate(corporate);

        return ResultFactory.getSuccessResult(contactPersonList);

    }
}
