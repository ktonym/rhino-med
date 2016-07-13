package ke.co.rhino.claim.service;

import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.claim.entity.ClaimBatch;
import ke.co.rhino.claim.repo.BillRepo;
import ke.co.rhino.claim.repo.ClaimBatchRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Created by akipkoech on 25/04/2016.
 */
@Service("claimBatchService")
@Transactional
public class ClaimBatchService extends AbstractService implements IClaimBatchService {

    @Autowired
    private ClaimBatchRepo repo;
    @Autowired
    private BillRepo billRepo;

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<ClaimBatch> create(LocalDate batchDate, int size, String username) {

        if(batchDate==null){
            batchDate = LocalDate.now();
        }

        ClaimBatch  batch = new ClaimBatch.ClaimBatchBuilder(generateBatchNo(),batchDate,size)
                .username(username)
                .build();

        repo.save(batch);

        return ResultFactory.getSuccessResult(batch);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<ClaimBatch> update(Long idClaimBatch, String batchNo,int size, LocalDate batchDate, String username) {

        ClaimBatch batch = repo.findByBatchNo(batchNo);

        if(!batch.getBatchNo().equals(idClaimBatch)){
            return ResultFactory.getFailResult("Batch number does not match the batch ID provided.");
        }

        ClaimBatch batch1 = new ClaimBatch.ClaimBatchBuilder(batchNo,batchDate,size)
                .username(username).idClaimBatch(idClaimBatch).build();

        repo.save(batch1);

        return ResultFactory.getSuccessResult(batch1);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<ClaimBatch> remove(Long idClaimBatch, String actionUsername) {

        ClaimBatch batch = repo.findOne(idClaimBatch);

        if(billRepo.countByBatch(batch)>0){
            return ResultFactory.getFailResult("Batch has bills. Deleting would result in orphan records.");
        }

        repo.delete(batch);

        String msg = "Claim batch ID ["+idClaimBatch+"] was deleted by " +actionUsername;

        return ResultFactory.getSuccessResult(msg);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<ClaimBatch>> findAll(int pageNum, int size) {

        PageRequest request = new PageRequest(pageNum-1,size);
        Page<ClaimBatch> claimBatchPage = repo.findAll(request);

        return ResultFactory.getSuccessResult(claimBatchPage);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<ClaimBatch>> findByDate(int pageNum,int size, LocalDate batchDate) {

        PageRequest request = new PageRequest(pageNum-1,size);
        Page<ClaimBatch> claimBatchPage = repo.findByBatchDate(batchDate,request);

        return ResultFactory.getSuccessResult(claimBatchPage);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<ClaimBatch>> findByUser(int pageNum,int size ,String username) {

        PageRequest request = new PageRequest(pageNum-1,size);
        Page<ClaimBatch> claimBatchPage = repo.findByUsername(username,request);
        return ResultFactory.getSuccessResult(claimBatchPage);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    private String generateBatchNo(){
        StringBuilder batNo = new StringBuilder("BAT-");
        long maxBat = repo.count();
        maxBat++;
        return batNo.append(maxBat).toString();
    }
}
