package ke.co.rhino.claim.repo;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.claim.entity.Bill;
import ke.co.rhino.claim.entity.ClaimBatch;
import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Created by akipkoech on 21/04/2016.
 */
public interface BillRepo extends JpaRepository<Bill,Long> {

    Page<Bill> findByInvoiceDate(LocalDate invoiceDate, Pageable pageable);

    Page<Bill> findByInvoiceDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    Page<Bill> findByProvider(ServiceProvider provider, Pageable pageable);

    Page<Bill> findByEnteredDate(LocalDate enteredDate, Pageable pageable);

    Page<Bill> findByBatch(ClaimBatch batch, Pageable pageable);

    Page<Bill> findByCorpMemberBenefit(CorpMemberBenefit corpMemberBenefit, Pageable pageable);

    Page<Bill> findByCorpMemberBenefit_Benefit_Category_CorpAnniv(CorpAnniv corpAnniv, Pageable pageable);

    long countByCorpMemberBenefit(CorpMemberBenefit corpMemberBenefit);

    long countByBatch(ClaimBatch batch);

    Bill findByClaimNo(String claimNo);

    Bill findByProviderAndInvoiceNo(ServiceProvider serviceProvider,String invoiceNo);

}
