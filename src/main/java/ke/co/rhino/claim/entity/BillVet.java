package ke.co.rhino.claim.entity;

import ke.co.rhino.fin.entity.Payment;
import ke.co.rhino.fin.entity.PaymentVoucher;
import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by ktonym on 1/10/15.
 */
@Entity //TODO consider decomposing this into fields{vetStatus,vetDate} in Bill
public class BillVet extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBillVet;
    @OneToOne(optional=false)
    private Bill bill;
    @Column(nullable = false)
    private LocalDate billVetDate;
    private String narration;
//    @OneToOne(mappedBy = "billVet")
//    private Payment payment; //circular reference??
    @ManyToOne(optional = true)
    private PaymentVoucher paymentVoucher;
    @Column(nullable = false)
    private VetStatus vetStatus;

    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public BillVet() {
    }

    public Long getIdBillVet() {
        return idBillVet;
    }

    public void setIdBillVet(Long idBillVet) {
        this.idBillVet = idBillVet;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public LocalDate getBillVetDate() {
        return billVetDate;
    }

    public void setBillVetDate(LocalDate billVetDate) {
        this.billVetDate = billVetDate;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public PaymentVoucher getPaymentVoucher() {
        return paymentVoucher;
    }

    public void setPaymentVoucher(PaymentVoucher paymentVoucher) {
        this.paymentVoucher = paymentVoucher;
    }

    public VetStatus getVetStatus() {
        return vetStatus;
    }

    public void setVetStatus(VetStatus vetStatus) {
        this.vetStatus = vetStatus;
    }

    @Override
    public Long getId() {
        return idBillVet;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder.add("idBillVet", idBillVet)
                .add("billVetDate",billVetDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(billVetDate))
                .add("narration",narration)
                .add("vetStatus", vetStatus.toString());

        bill.addJson(builder);
        if(paymentVoucher!=null){
            paymentVoucher.addJson(builder);
        }

    }
}
