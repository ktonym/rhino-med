package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.BenefitRef;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by akipkoech on 25/05/15.
 */
public interface BenefitRefRepo extends JpaRepository<BenefitRef, Long> {
    BenefitRef findByBenefitName(String benefitName);
    @Query("SELECT b FROM BenefitRef b WHERE UPPER(b.benefitName)= UPPER(:name)")
    BenefitRef findByName(@Param("name") String name);
    Page<BenefitRef> findByBenefitNameLike(String searchStr,Pageable pageable);
    Page<BenefitRef> findByLastUpdateAfter(LocalDateTime mTime,Pageable pageable);
}
