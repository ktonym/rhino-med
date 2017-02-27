package ke.co.rhino.claim.entity;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.entity.EntityItem;
import ke.co.rhino.uw.entity.LocalDatePersistenceConverter;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 21/04/2016.
 */
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"invoice_no","provider_id"})})
public class Bill extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBill;
    @Column(name="invoice_no",nullable = false)
    private String invoiceNo;
    @Column(nullable = false, unique = true)
    private String claimNo;
    @Convert(converter=LocalDatePersistenceConverter.class)
    @Column(nullable = false)
    private LocalDate invoiceDate;
    @Column(nullable = false)
    private BigDecimal invoiceAmt;
    @Column(nullable = true)
    private BigDecimal deductionAmt;
    private String deductionReason;
    @Convert(converter=LocalDatePersistenceConverter.class)
    @Column(nullable = false)
    private LocalDate enteredDate;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "idMember",referencedColumnName = "idMember",nullable = false),
            @JoinColumn(name = "idCorpAnniv",referencedColumnName = "idCorpAnniv",nullable = false),
            @JoinColumn(name = "idCorpBenefit",referencedColumnName = "idCorpBenefit", nullable = false)
    })
    private CorpMemberBenefit corpMemberBenefit;
    @ManyToOne
    @JoinColumn(name = "provider_id",nullable = false)
    private ServiceProvider provider;
    @ManyToOne
    @JoinColumn(name = "batch_id",nullable = false)
    private ClaimBatch batch;
    @OneToOne(mappedBy = "bill")
    private BillVet billVet;
    @OneToMany(mappedBy = "bill")
    private List<PreAuthBill> preAuthBills;
    /*@ManyToOne
    private Treatment treatment;*/

    public Bill() {
    }

    public Bill(BillBuilder billBuilder) {
        this.idBill = billBuilder.idBill;
        //this.treatment = billBuilder.treatment;
        this.corpMemberBenefit = billBuilder.corpMemberBenefit;
        this.invoiceNo = billBuilder.invoiceNo;
        this.claimNo = billBuilder.claimNo;
        this.invoiceDate = billBuilder.invoiceDate;
        this.invoiceAmt = billBuilder.invoiceAmt;
        this.deductionAmt = billBuilder.deductionAmt;
        this.deductionReason = billBuilder.deductionReason;
        this.enteredDate = billBuilder.enteredDate;
        this.provider = billBuilder.provider;
        this.batch = billBuilder.batch;
    }

    public static class BillBuilder{

        private Long idBill;
        private final CorpMemberBenefit corpMemberBenefit;
        private String invoiceNo;
        private String claimNo;
        private LocalDate invoiceDate;
        private BigDecimal invoiceAmt;
        private BigDecimal deductionAmt;
        private String deductionReason;
        private LocalDate enteredDate;
        private ServiceProvider provider;
        private ClaimBatch batch;
        //public Treatment treatment;

        public BillBuilder(CorpMemberBenefit corpMemberBenefit) {
            this.corpMemberBenefit = corpMemberBenefit;
        }

        public BillBuilder idBill(Long idBill){
            this.idBill = idBill;
            return this;
        }

        public BillBuilder invoiceNo(String invoiceNo){
            this.invoiceNo = invoiceNo;
            return this;
        }

        public BillBuilder claimNo(String claimNo){
            this.claimNo = claimNo;
            return this;
        }

        public BillBuilder invoiceDate(LocalDate invoiceDate){
            this.invoiceDate = invoiceDate;
            return this;
        }

        public BillBuilder invoiceAmt(BigDecimal invoiceAmt){
            this.invoiceAmt = invoiceAmt;
            return this;
        }

        public BillBuilder deductionAmt(BigDecimal deductionAmt){
            this.deductionAmt = deductionAmt;
            return this;
        }

        public BillBuilder deductionReason(String deductionReason){
            this.deductionReason = deductionReason;
            return this;
        }

        public BillBuilder enteredDate(LocalDate enteredDate){
            this.enteredDate = enteredDate;
            return this;
        }

        public BillBuilder provider(ServiceProvider provider){
            this.provider = provider;
            return this;
        }

        public BillBuilder batch(ClaimBatch batch){
            this.batch = batch;
            return this;
        }

        public Bill build(){
            return new Bill(this);
        }

    }

    public Long getIdBill() {
        return idBill;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public String getClaimNo() {
        return claimNo;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public BigDecimal getInvoiceAmt() {
        return invoiceAmt;
    }

    public BigDecimal getDeductionAmt() {
        return deductionAmt;
    }

    public String getDeductionReason() {
        return deductionReason;
    }

    public LocalDate getEnteredDate() {
        return enteredDate;
    }

    /*public CorpMemberBenefit getCorpMemberBenefit() {
        return corpMemberBenefit;
    }*/

    public ServiceProvider getProvider() {
        return provider;
    }

    public ClaimBatch getBatch() {
        return batch;
    }

    public BillVet getBillVet() {
        return billVet;
    }

    public List<PreAuthBill> getPreAuthBills() {
        return preAuthBills;
    }

    @Override
    public Long getId() {
        return idBill;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idBill",idBill)
                .add("invoiceNo",invoiceNo)
                .add("claimNo", claimNo)
                .add("invoiceDate", invoiceDate==null ? "" : DATE_FORMATTER_yyyyMMdd.format(invoiceDate))
                .add("invoiceAmt", invoiceAmt)
                .add("deductionAmt",deductionAmt)
                .add("deductionReason",deductionReason)
                .add("enteredDate",enteredDate==null ? "" : DATE_FORMATTER_yyyyMMdd.format(enteredDate));

        /*if(treatment!=null){
            treatment.addJson(builder);
        }*/

        if(batch!=null){
            batch.addJson(builder);
        }

        if(provider!=null){
            provider.addJson(builder);
        }

    }
}
