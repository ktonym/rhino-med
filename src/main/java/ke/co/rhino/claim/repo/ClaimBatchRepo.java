package ke.co.rhino.claim.repo;

import ke.co.rhino.claim.entity.ClaimBatch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Created by akipkoech on 21/04/2016.
 */
public interface ClaimBatchRepo extends JpaRepository<ClaimBatch, Long> {

    ClaimBatch findByBatchNo(String batchNo);
    Page<ClaimBatch> findByBatchDate(LocalDate batchDate, Pageable pageable);
    Page<ClaimBatch> findByUsername(String username, Pageable pageable);

}
