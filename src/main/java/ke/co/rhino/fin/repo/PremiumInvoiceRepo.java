package ke.co.rhino.fin.repo;

import ke.co.rhino.fin.entity.PremiumInvoice;
import ke.co.rhino.uw.entity.CorpBenefit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Created by akipkoech on 21/04/2016.
 */
public interface PremiumInvoiceRepo extends PagingAndSortingRepository<PremiumInvoice,Long> {

    Page<PremiumInvoice> findByBenefit(CorpBenefit benefit,Pageable pageable);

    long countByBenefit(CorpBenefit benefit);

    Optional<PremiumInvoice> getOne(Long idPremiumInvoice);

    Optional<PremiumInvoice> findByParentInvoice(PremiumInvoice premiumInvoice);

}
