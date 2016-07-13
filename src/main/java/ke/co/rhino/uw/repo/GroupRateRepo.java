package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.BenefitRef;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.GroupRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by akipkoech on 12/9/14.
 */
public interface GroupRateRepo extends JpaRepository<GroupRate,Long> {

    Page<GroupRate> findByCorporate(Corporate corporate, Pageable pageable);

    //List<GroupRate>

    GroupRate findByCorporateAndBenefitRefAndFamilySizeAndUpperLimit(
            Corporate corporate,BenefitRef benefitRef, String familySize, BigDecimal limit);

    List<GroupRate> findByCorporateAndBenefitRefAndFamilySize(Corporate corporate,BenefitRef benefitRef, String familySize);

}
