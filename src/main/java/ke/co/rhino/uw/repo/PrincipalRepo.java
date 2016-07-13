package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.Category;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.Principal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by akipkoech on 12/8/14.
 */
public interface PrincipalRepo extends JpaRepository<Principal,Long> {

    //List<Principal> findByCategoryPrincipal_Category_Anniv_Corporate(Corporate corporate);
    //@Query("select p from Principal p where p.corporate = :corporate")
	//List<Principal> findByCorporate(@Param("corporate") Corporate corporate);
    List<Principal> findByCorporate(Corporate corporate);

    Page<Principal> findByCorporate(Corporate corporate, Pageable pageable);

    //TODO test whether this is efficient (or even possible)
    Page<Principal> findByCategoryPrincipals_Category(Category category, Pageable pageable);

    @Query("SELECT COUNT(p)+1 FROM Principal p where p.corporate = :corp")
    long getCountPlusOne(@Param("corp") Corporate corporate);

    long countByCorporate(Corporate corporate);

    Principal findByFamilyNo(String familyNo);

}
