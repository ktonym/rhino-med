package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.BenefitRef;
import ke.co.rhino.uw.entity.StdPremiumRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by user on 07/04/2017.
 */
public interface StdPremiumRateRepo extends PagingAndSortingRepository<StdPremiumRate,Long>, JpaSpecificationExecutor<StdPremiumRate>{
    Page<StdPremiumRate> findByCorpBenefit(BenefitRef benefitRef, Pageable pageable);

    Page<StdPremiumRate> findByFamilySize(String familySize, Pageable pageable);
}
