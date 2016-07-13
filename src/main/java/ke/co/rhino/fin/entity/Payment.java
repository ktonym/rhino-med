package ke.co.rhino.fin.entity;

import ke.co.rhino.claim.entity.AbstractEntity;
import ke.co.rhino.claim.entity.BillVet;
import ke.co.rhino.uw.entity.EntityItem;
import ke.co.rhino.uw.entity.LocalDatePersistenceConverter;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by ktonym on 1/9/15.
 */
@Entity
public class Payment extends AbstractEntity implements EntityItem<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPayment;
    @ManyToOne
    @JoinColumn(name = "bank_id",nullable = false)
    private BankDetail bankDetail;
    @Column(nullable = false,unique = true)
    private String chequeNo;
    @Column(nullable = false)
    private BigDecimal chequeAmt;
    @Column(nullable = false)
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate chequeDate;
    @Column(nullable = false)
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dispatchDate;
    @OneToOne(optional=false)
    private PaymentVoucher voucher;
    @OneToOne(mappedBy = "payment")
    private PaymentCancellation paymentCancellation;
    @OneToOne(optional=false)
    private BillVet billVet;

    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    public Payment() {
    }

    public Integer getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(Integer idPayment) {
        this.idPayment = idPayment;
    }

    public BankDetail getBankDetail() {
        return bankDetail;
    }

    public void setBankDetail(BankDetail bankDetail) {
        this.bankDetail = bankDetail;
    }

    public String getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }

    public BigDecimal getChequeAmt() {
        return chequeAmt;
    }

    public void setChequeAmt(BigDecimal chequeAmt) {
        this.chequeAmt = chequeAmt;
    }

    public LocalDate getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(LocalDate chequeDate) {
        this.chequeDate = chequeDate;
    }

    public LocalDate getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(LocalDate dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public PaymentVoucher getVoucher() {
        return voucher;
    }

    public void setVoucher(PaymentVoucher voucher) {
        this.voucher = voucher;
    }

    public PaymentCancellation getPaymentCancellation() {
        return paymentCancellation;
    }

    public void setPaymentCancellation(PaymentCancellation paymentCancellation) {
        this.paymentCancellation = paymentCancellation;
    }

	public BillVet getBillVet() {
		return billVet;
	}

	public void setBillVet(BillVet billVet) {
		this.billVet = billVet;
	}


    @Override
    public Integer getId() {
        return idPayment;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder.add("idPayment",idPayment);
        bankDetail.addJson(builder);
        builder.add("chequeNo",chequeNo)
                .add("chequeAmt",chequeAmt)
                .add("chequeDate", DATE_FORMATTER_yyyyMMdd.format(chequeDate))
                .add("dispatchDate", dispatchDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(dispatchDate));
        voucher.addJson(builder);
        billVet.addJson(builder);

    }
}
