package ke.co.rhino.claim.repo;

import ke.co.rhino.claim.entity.Assessment;
import ke.co.rhino.claim.entity.Condition;
import ke.co.rhino.uw.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by user on 07/02/2017.
 */
public interface AssessmentRepo extends PagingAndSortingRepository<Assessment,Long> {

    List<Assessment> findByMember(Member member);
    List<Assessment> findByCondition(Condition condition);
    Optional<Assessment> getOne(Long assessmentId);
}
