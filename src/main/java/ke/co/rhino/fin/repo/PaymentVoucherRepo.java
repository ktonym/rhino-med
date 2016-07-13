package ke.co.rhino.fin.repo;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.claim.entity.Bill;
import ke.co.rhino.claim.entity.BillVet;
import ke.co.rhino.claim.entity.ClaimBatch;
import ke.co.rhino.fin.entity.PaymentVoucher;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Created by akipkoech on 25/06/2016.
 */
public interface PaymentVoucherRepo extends JpaRepository<PaymentVoucher,Long> {

    //PaymentVoucher findByBillVet(BillVet billVet);
    PaymentVoucher findByBillVetList_Bill(Bill bill);
    Page<PaymentVoucher> findByBillVetList_Bill_Provider(ServiceProvider provider, Pageable pageable);

    //To find all payments made to a certain member benefit
    Page<PaymentVoucher> findByBillVetList_Bill_CorpMemberBenefit(CorpMemberBenefit corpMemberBenefit, Pageable pageable);

    //To find all payments made to a certain claim batch
    Page<PaymentVoucher> findByBillVetList_Bill_Batch(ClaimBatch batch,Pageable pageable);

    //To find all unauthorized vouchers
    Page<PaymentVoucher> findByAuthorizedDateIsNull(Pageable pageable); //TODO check syntax

    //To find all vouchers done on a certain date
    Page<PaymentVoucher> findByVoucherDate(LocalDate voucherDate, Pageable pageable);

    //To find all vouchers done on a certain date range
    Page<PaymentVoucher> findByVoucherDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    //To find all vouchers authorized on a certain date range
    Page<PaymentVoucher> findByAuthorizedDateBetween(LocalDate start, LocalDate end, Pageable pageable);

    //To find all vouchers authorized on a certain date
    Page<PaymentVoucher> findByAuthorizedDate(LocalDate authorizedDate, Pageable pageable);
}
