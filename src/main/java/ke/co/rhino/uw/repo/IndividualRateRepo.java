package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.BenefitRef;
import ke.co.rhino.uw.entity.IndividualRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;

/**
 * Created by akipkoech on 12/9/14.
 */
public interface IndividualRateRepo extends PagingAndSortingRepository<IndividualRate,Long>, JpaSpecificationExecutor<IndividualRate> {

    Page<IndividualRate> findByBenefitRefAndMinAgeAndMaxAgeAndUpperLimit(BenefitRef benefitRef, Integer minAge, Integer maxAge, BigDecimal upperLimit, Pageable pageable);

}
