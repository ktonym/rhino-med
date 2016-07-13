package ke.co.rhino.claim.repo;

import ke.co.rhino.claim.entity.Bill;
import ke.co.rhino.claim.entity.PreAuthBill;
import ke.co.rhino.claim.entity.PreAuthBillId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by akipkoech on 21/04/2016.
 */
public interface PreAuthBillRepo extends JpaRepository<PreAuthBill,PreAuthBillId> {
    long countByBill(Bill bill);
}
