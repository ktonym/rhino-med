package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.BenefitRef;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.PremiumRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by akipkoech on 12/9/14.
 */
public interface PremiumRateRepo extends JpaRepository<PremiumRate,Integer> {

//    PremiumRate findByCorporateAndFamilySizeAndBenefitRefAndUpperLimit(Corporate corporate,
//                                                                       String famSize,
//                                                                       BenefitRef benefitRef,
//                                                                       BigDecimal upperLimit);
//
//    List<PremiumRate> findByCorporate(Corporate corporate);

  //TODO create generic methods here

}
