package ke.co.rhino.claim.service;

import ke.co.rhino.claim.entity.Bill;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 21/04/2016.
 */
public interface IBillService {

    Result<Bill> create(String invoiceNo,
                        //String claimNo,
                        LocalDate invoiceDate,
                        BigDecimal invoiceAmt,
                        BigDecimal deductionAmt,
                        String deductionReason,
                        //LocalDate enteredDate,
                        String memberNo,
                        Long idCorpAnniv,
                        Long idCorpBenefit,
                        Long idProvider,
                        String batchNo,
                        String actionUsername);

    Result<Bill> update(Long idBill,
                        String invoiceNo,
                        String claimNo,
                        LocalDate invoiceDate,
                        BigDecimal invoiceAmt,
                        BigDecimal deductionAmt,
                        String deductionReason,
                        //LocalDate enteredDate,
                        String memberNo,
                        Long idCorpAnniv,
                        Long idCorpBenefit,
                        Long idProvider,
                        Long idClaimBatch,
                        String actionUsername);

    Result<Bill> remove(Long idBill, String actionUsername);

    Result<Page<Bill>> findAll(int pageNum, int size,String actionUsername);

    Result<Page<Bill>> findByAnniv(int pageNum, int size,Long idCorpAnniv, String actionUsername);


}
