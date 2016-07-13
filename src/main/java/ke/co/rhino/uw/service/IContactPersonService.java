package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.ContactPerson;
import ke.co.rhino.uw.vo.Result;

import java.util.List;

/**
 * Created by akipkoech on 02/02/2016.
 */
public interface IContactPersonService {
    Result<ContactPerson> store(Long idContactPerson,
                                Long idCorporate,
                                String firstName,
                                String surname,
                                String jobTitle,
                                String email,
                                String tel,
                                String actionUsername);

//    Result<ContactPerson> create(Long idCorporate,
//                                String firstName,
//                                String surname,
//                                String jobTitle,
//                                String email,
//                                String tel,
//                                String actionUsername);

    Result<ContactPerson> remove(Long idContactPerson, String actionUsername);
    Result<List<ContactPerson>> findAll(Long idCorporate, String actionUsername);

}
