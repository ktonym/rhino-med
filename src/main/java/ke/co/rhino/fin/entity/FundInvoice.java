package ke.co.rhino.fin.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by akipkoech on 13/06/2016.
 */
@Entity
public class FundInvoice extends AbstractEntity implements EntityItem<Long> {

    @Id
    private Long idFundInvoice;

    //private FundInvoiceType invoiceType;
    @ManyToOne //TODO check whether it's feasible to use @OneToMany instead
    private AdminFee adminFee;
    private BigDecimal amount;
    private LocalDate invoiceDate;
    @OneToOne
    private CorpBenefit benefit;
    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public FundInvoice() {
    }

    public FundInvoice(FundInvoiceBuilder fundInvoiceBuilder) {
        this.benefit = fundInvoiceBuilder.benefit;
        this.amount = fundInvoiceBuilder.amount;
        this.invoiceDate = fundInvoiceBuilder.invoiceDate;
        this.adminFee = fundInvoiceBuilder.adminFee;
    }

    public static class FundInvoiceBuilder{
        private final CorpBenefit benefit;
        private final BigDecimal amount;
        private final LocalDate invoiceDate;
        private AdminFee adminFee;

        public FundInvoiceBuilder(CorpBenefit benefit, BigDecimal amount, LocalDate invoiceDate) {
            this.benefit = benefit;
            this.amount = amount;
            this.invoiceDate = invoiceDate;
        }

        public FundInvoiceBuilder adminFee(AdminFee adminFee){
            this.adminFee = adminFee;
            return this;
        }

        public FundInvoice build(){
            return new FundInvoice(this);
        }

    }

    @Override
    public Long getId() {
        return idFundInvoice;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("amount", amount.toString())
                .add("invoiceDate",invoiceDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(invoiceDate));
        benefit.addJson(builder);
    }

    public Long getIdFundInvoice() {
        return idFundInvoice;
    }

    public AdminFee getAdminFee() {
        return adminFee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public CorpBenefit getBenefit() {
        return benefit;
    }
}
