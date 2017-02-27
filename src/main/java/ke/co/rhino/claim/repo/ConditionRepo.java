package ke.co.rhino.claim.repo;

import ke.co.rhino.claim.entity.Condition;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Created by user on 08/02/2017.
 */
public interface ConditionRepo extends PagingAndSortingRepository<Condition,Long> {

    Optional<Condition> getOne(Long conditionId);

}
