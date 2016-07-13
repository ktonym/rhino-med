package ke.co.rhino.fin.repo;

import ke.co.rhino.fin.entity.PremiumInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by akipkoech on 21/04/2016.
 */
public interface PremiumInvoiceRepo extends JpaRepository<PremiumInvoice,Long> {
}
