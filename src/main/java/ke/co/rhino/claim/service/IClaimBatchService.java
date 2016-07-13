package ke.co.rhino.claim.service;

import ke.co.rhino.claim.entity.ClaimBatch;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 22/04/2016.
 */
public interface IClaimBatchService {

    Result<ClaimBatch> create(LocalDate batchDate, int size, String username);

    Result<ClaimBatch> update(Long idClaimBatch, String batchNo, int size, LocalDate batchDate, String username);

    Result<ClaimBatch> remove(Long idClaimBatch,String actionUsername);

    Result<Page<ClaimBatch>> findAll(int pageNum,int size);

    Result<Page<ClaimBatch>> findByDate(int pageNum,int size,LocalDate batchDate);

    Result<Page<ClaimBatch>> findByUser(int pageNum,int size,String username);

}
