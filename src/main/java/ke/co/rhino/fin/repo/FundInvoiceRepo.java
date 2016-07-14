package ke.co.rhino.fin.repo;

import ke.co.rhino.fin.entity.FundInvoice;
import ke.co.rhino.uw.entity.CorpBenefit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by akipkoech on 25/06/2016.
 */
public interface FundInvoiceRepo extends JpaRepository<FundInvoice,Long> {

    Page<FundInvoice> findByBenefit(CorpBenefit benefit,Pageable pageable);

    long countByBenefit(CorpBenefit benefit);

}
