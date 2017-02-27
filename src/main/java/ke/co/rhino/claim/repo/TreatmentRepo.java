package ke.co.rhino.claim.repo;

import ke.co.rhino.claim.entity.Treatment;
import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 07/02/2017.
 */
public interface TreatmentRepo extends JpaRepository<Treatment,Long> {

    Page<Treatment> findByCorpMemberBenefit(CorpMemberBenefit corpMemberBenefit, Pageable pageable);
    Page<Treatment> findByCorpMemberBenefit_Benefit_Category_CorpAnniv(CorpAnniv corpAnniv, Pageable pageable);
    Page<Treatment> findByAssessmentMember(Member member, Pageable pageable);
    long countByCorpMemberBenefit(CorpMemberBenefit corpMemberBenefit);

}
