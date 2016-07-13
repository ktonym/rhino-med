package ke.co.rhino.fin.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;
import ke.co.rhino.uw.entity.LocalDatePersistenceConverter;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by ktonym on 1/11/15.
 */
@Entity
public class PaymentCancellation extends AbstractEntity implements EntityItem<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPaymentCancellation;
    @OneToOne(optional=false)
    private Payment payment;
    @Convert(converter = LocalDatePersistenceConverter.class)
    @Column(nullable = false)
    private LocalDate paymentCancellationDate;
    @Column(nullable = false)
    private String reason;

    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public PaymentCancellation() {
    }

    public Integer getIdPaymentCancellation() {
        return idPaymentCancellation;
    }

    public void setIdPaymentCancellation(Integer idPaymentCancellation) {
        this.idPaymentCancellation = idPaymentCancellation;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public LocalDate getPaymentCancellationDate() {
        return paymentCancellationDate;
    }

    public void setPaymentCancellationDate(LocalDate paymentCancellationDate) {
        this.paymentCancellationDate = paymentCancellationDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Integer getId() {
        return idPaymentCancellation;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder.add("idPaymentCancellation",idPaymentCancellation);
        payment.addJson(builder);
        builder.add("paymentCancellationDate",DATE_FORMATTER_yyyyMMdd.format(paymentCancellationDate) )
                .add("reason",reason);

    }
}
