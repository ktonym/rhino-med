package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by akipkoech on 12/8/14.
 */
public interface CorpBenefitRepo extends JpaRepository<CorpBenefit,Long> {

    Page<CorpBenefit> findByCategory(Category category, Pageable pageable);

    long countByCategory(Category category);

    Page<CorpBenefit> findByCategory_CorpAnniv(CorpAnniv anniv, Pageable pageable);

    Page<CorpBenefit> findByCategory_CorpAnniv_Corporate(Corporate corporate, Pageable pageable);

    //To find insured benefits in current anniversary
    Page<CorpBenefit> findByCategory_CorpAnnivAndBenefitType(CorpAnniv anniv, BenefitType benefitType, Pageable pageable);

    @Query("SELECT c FROM CorpBenefit c WHERE c.parentBenefit IS NULL AND c.category = :category")
    List<CorpBenefit> findMainBenefits(@Param("category") Category category);

    @Query("SELECT c FROM CorpBenefit c WHERE c.parentBenefit = :parentBenefit")
    List<CorpBenefit> findSubBenefits(@Param("parentBenefit") CorpBenefit parentBenefit);

    List<CorpBenefit> findByParentBenefit(CorpBenefit parentBenefit); //will give subBenefits

    CorpBenefit findByCategoryAndBenefitRef(Category category,BenefitRef benefitRef);
}
