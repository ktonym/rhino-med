package ke.co.rhino.fin.entity;

import ke.co.rhino.claim.entity.BillVet;
import ke.co.rhino.claim.entity.ClaimBatch;
import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;
import ke.co.rhino.uw.entity.LocalDatePersistenceConverter;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by ktonym on 1/9/15.
 */

@Entity
public class PaymentVoucher extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPaymentVoucher;
    @Column(nullable = false,unique = true)
    private String voucherNo;
    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(nullable = false,unique = true)
    private LocalDate voucherDate;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate authorizedDate;
    @OneToOne(mappedBy = "voucher") //voucher comes before payment.
    private Payment payment;
//    @OneToMany(mappedBy = "voucher")
//    private List<ClaimBatch> batch;
    @OneToMany(mappedBy = "paymentVoucher")
    private List<BillVet> billVetList;

    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public PaymentVoucher() {
    }

    public PaymentVoucher(PaymentVoucherBuilder paymentVoucherBuilder) {
        this.idPaymentVoucher = paymentVoucherBuilder.idPaymentVoucher;
        this.voucherNo = paymentVoucherBuilder.voucherNo;
        this.billVetList = paymentVoucherBuilder.billVetList;
        this.voucherDate = paymentVoucherBuilder.voucherDate;
        this.authorizedDate = paymentVoucherBuilder.authorizedDate;
    }

    public static class PaymentVoucherBuilder{
        private Long idPaymentVoucher;
        private final String voucherNo;
        private final List<BillVet> billVetList;
        private LocalDate voucherDate;
        private LocalDate authorizedDate;

        public PaymentVoucherBuilder(String voucherNo, List<BillVet> billVetList) {
            this.voucherNo = voucherNo;
            this.billVetList = billVetList;
        }

        public PaymentVoucherBuilder idPaymentVoucher(Long idPaymentVoucher){
            this.idPaymentVoucher = idPaymentVoucher;
            return this;
        }

        public PaymentVoucherBuilder voucherDate(LocalDate voucherDate){
            this.voucherDate = voucherDate;
            return this;
        }

        public PaymentVoucherBuilder authorizedDate(LocalDate authorizedDate){
            this.authorizedDate = authorizedDate;
            return this;
        }

        public PaymentVoucher build(){
            return new PaymentVoucher(this);
        }

    }


    @Override
    public Long getId() {
        return idPaymentVoucher;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder.add("idPaymentVoucher",idPaymentVoucher)
                .add("voucherNo",voucherNo)
                .add("voucherDate",DATE_FORMATTER_yyyyMMdd.format(voucherDate))
                .add("authorizedDate", DATE_FORMATTER_yyyyMMdd.format(authorizedDate));

//        billVet.addJson(builder);
//TODO manually include the child elements at the web handling layer.
    }
}
