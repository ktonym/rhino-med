package ke.co.rhino.fin.repo;

import ke.co.rhino.fin.entity.FundInvoice;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.Corporate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by akipkoech on 25/06/2016.
 */
public interface FundInvoiceRepo extends JpaRepository<FundInvoice,Long> {

    Page<FundInvoice> findByBenefit(CorpBenefit benefit,Pageable pageable);

    Page<FundInvoice> findByBenefit_Category_CorpAnniv_Corporate(Corporate corporate, Pageable pageable);

    FundInvoice findByInvoiceNumber(String invoiceNumber);

    long countByBenefit(CorpBenefit benefit);

}
