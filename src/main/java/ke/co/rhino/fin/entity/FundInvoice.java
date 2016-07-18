package ke.co.rhino.fin.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by akipkoech on 13/06/2016.
 */
@Entity
public class FundInvoice extends AbstractEntity implements EntityItem<Long> {

    @Id
    private Long idFundInvoice;
    @Column(nullable = false,unique = true)
    private String invoiceNumber;
    @OneToMany(mappedBy = "fundInvoice")
    private List<AdminFee> adminFeeList;
    private BigDecimal amount;
    private LocalDate invoiceDate;
    @OneToOne
    private CorpBenefit benefit;
    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public FundInvoice() {
    }

    public FundInvoice(FundInvoiceBuilder fundInvoiceBuilder) {
        this.idFundInvoice = fundInvoiceBuilder.idFundInvoice;
        this.invoiceNumber = fundInvoiceBuilder.invoiceNumber;
        this.benefit = fundInvoiceBuilder.benefit;
        this.amount = fundInvoiceBuilder.amount;
        this.invoiceDate = fundInvoiceBuilder.invoiceDate;
        this.adminFeeList = fundInvoiceBuilder.adminFeeList;
    }

    public static class FundInvoiceBuilder{
        private Long idFundInvoice;
        private String invoiceNumber;
        private final CorpBenefit benefit;
        private final BigDecimal amount;
        private final LocalDate invoiceDate;
        private List<AdminFee> adminFeeList;

        public FundInvoiceBuilder(CorpBenefit benefit, BigDecimal amount, LocalDate invoiceDate) {
            this.benefit = benefit;
            this.amount = amount;
            this.invoiceDate = invoiceDate;
        }

        public FundInvoiceBuilder idFundInvoice(Long idFundInvoice){
            this.idFundInvoice = idFundInvoice;
            return this;
        }

        public FundInvoiceBuilder invoiceNumber(String invoiceNumber){
            this.invoiceNumber = invoiceNumber;
            return this;
        }

        public FundInvoiceBuilder adminFeeList(List<AdminFee> adminFeeList){
            this.adminFeeList = adminFeeList;
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
        builder.add("idFundInvoice",idFundInvoice)
                .add("invoiceNumber", invoiceNumber)
                .add("amount", amount.toString())
                .add("invoiceDate",invoiceDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(invoiceDate));
        benefit.addJson(builder);
    }

    public Long getIdFundInvoice() {
        return idFundInvoice;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public List<AdminFee> getAdminFeeList() {
        return adminFeeList;
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
