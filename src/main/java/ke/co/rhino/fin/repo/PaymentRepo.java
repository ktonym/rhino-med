package ke.co.rhino.fin.repo;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.fin.entity.BankDetail;
import ke.co.rhino.fin.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by akipkoech on 25/06/2016.
 */
public interface PaymentRepo extends JpaRepository<Payment,Long> {

    Page<Payment> findByBankDetail(BankDetail bankDetail, Pageable pageable);

    Page<Payment> findByBankDetail_ServiceProvider(ServiceProvider serviceProvider, Pageable pageable);
}
