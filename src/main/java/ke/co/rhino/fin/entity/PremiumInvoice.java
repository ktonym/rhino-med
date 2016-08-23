package ke.co.rhino.fin.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.EntityItem;
import ke.co.rhino.uw.entity.LocalDatePersistenceConverter;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by akipkoech on 16/11/2015.
 */
@Entity
public class PremiumInvoice extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPremiumInvoice;
    @Column(unique = true,nullable = false)
    private String invoiceNumber;
    private BusinessClass businessClass;
    @OneToMany(mappedBy = "premiumInvoice")
    private List<PremiumInvoiceItem> invoiceItems;
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate invoiceDate;
    @OneToOne
    private CorpBenefit benefit;
    private Integer stampDuty;
    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    @OneToOne(mappedBy = "parentInvoice")
    private PremiumInvoice reversalInvoice;
    @OneToOne
    @JoinColumn(name = "idParentInvoice",unique = true)
    private PremiumInvoice parentInvoice;

    public PremiumInvoice(PremiumInvoiceBuilder premiumInvoiceBuilder) {
        this.idPremiumInvoice = premiumInvoiceBuilder.idPremiumInvoice;
        this.invoiceNumber = premiumInvoiceBuilder.invoiceNumber;
        this.businessClass = premiumInvoiceBuilder.businessClass;
        this.invoiceDate = premiumInvoiceBuilder.invoiceDate;
        this.benefit = premiumInvoiceBuilder.benefit;
        this.stampDuty = premiumInvoiceBuilder.stampDuty;
        this.parentInvoice = premiumInvoiceBuilder.parentInvoice;
    }

    @Override
    public Long getId() {
        return idPremiumInvoice;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idPremiumInvoice",idPremiumInvoice)
                .add("invoiceNumber", invoiceNumber)
                .add("businessClass",businessClass.toString())
                .add("invoiceDate",invoiceDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(invoiceDate))
                .add("SD",stampDuty == null ? 0 : stampDuty);
        benefit.addJson(builder);
        parentInvoice.addJson(builder);
    }

    public PremiumInvoice() {
    }

    public static class PremiumInvoiceBuilder{
        private Long idPremiumInvoice;
        private final String invoiceNumber;
        private BusinessClass businessClass;
        private final LocalDate invoiceDate;
        private final CorpBenefit benefit;
        private Integer stampDuty;
        public PremiumInvoice parentInvoice;

        public PremiumInvoiceBuilder(String invoiceNumber, LocalDate invoiceDate, CorpBenefit benefit) {
            this.invoiceNumber = invoiceNumber;
            this.invoiceDate = invoiceDate;
            this.benefit = benefit;
        }

        public PremiumInvoiceBuilder businessClass(BusinessClass businessClass){
            this.businessClass = businessClass;
            return this;
        }

        public PremiumInvoiceBuilder idPremiumInvoice(Long idPremiumInvoice){
            this.idPremiumInvoice = idPremiumInvoice;
            return this;
        }

        public PremiumInvoiceBuilder stampDuty(Integer stampDuty){
            this.stampDuty = stampDuty;
            return this;
        }

        public PremiumInvoiceBuilder parentInvoice(PremiumInvoice parentInvoice){
            this.parentInvoice = parentInvoice;
            return this;
        }

        public PremiumInvoice build(){
            return new PremiumInvoice(this);
        }

    }

    public Long getIdPremiumInvoice() {
        return idPremiumInvoice;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public BusinessClass getBusinessClass() {
        return businessClass;
    }

    public List<PremiumInvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public CorpBenefit getBenefit() {
        return benefit;
    }

    public Integer getStampDuty() {
        return stampDuty;
    }
}
