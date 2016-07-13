package ke.co.rhino.fin.repo;

import ke.co.rhino.fin.entity.FundInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by akipkoech on 25/06/2016.
 */
public interface FundInvoiceRepo extends JpaRepository<FundInvoice,Long> {
}
