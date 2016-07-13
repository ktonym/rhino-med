package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.ContactPerson;
import ke.co.rhino.uw.entity.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by akipkoech on 27/01/2016.
 */
public interface ContactPersonRepo extends JpaRepository<ContactPerson,Long> {

    List<ContactPerson> findByCorporate(Corporate corporate);
    ContactPerson findByEmail(String email);
}
