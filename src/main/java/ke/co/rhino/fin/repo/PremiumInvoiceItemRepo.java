package ke.co.rhino.fin.repo;

import ke.co.rhino.fin.entity.PremiumInvoiceItem;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by akipkoech on 25/06/2016.
 */
public interface PremiumInvoiceItemRepo extends JpaRepository<PremiumInvoiceItem,Long> {
    long countByCorpMemberBenefit(CorpMemberBenefit benefit);
}
