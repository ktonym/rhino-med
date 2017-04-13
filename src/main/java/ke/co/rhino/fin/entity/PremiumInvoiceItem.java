package ke.co.rhino.fin.entity;



import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.entity.LocalDatePersistenceConverter;
import ke.co.rhino.uw.entity.PremiumRate;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Created by akipkoech on 16/11/2015.
 */
@Entity
public class PremiumInvoiceItem extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPremiumInvoiceItem;
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate invoiceDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPremiumRate")
    private PremiumRate premiumRate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPremiumInvoice")
    private PremiumInvoice premiumInvoice;
    @OneToOne(cascade = CascadeType.ALL)
    /*@JoinColumns({@JoinColumn(name="idMember", referencedColumnName = "idMember", nullable = false),
                  @JoinColumn(name = "idCorpAnniv", referencedColumnName = "idCorpAnniv", nullable = false),
                  @JoinColumn(name = "idCorpBenefit",referencedColumnName = "idCorpBenefit", nullable = false)})*/
    @JoinColumn(name = "idCorpMemberBenefit", referencedColumnName = "idCorpMemberBenefit", nullable = false)
    private CorpMemberBenefit corpMemberBenefit;
    private BigDecimal amount;

    public PremiumInvoiceItem(PremiumInvoiceItemBuilder premiumInvoiceItemBuilder) {
        this.invoiceDate = premiumInvoiceItemBuilder.invoiceDate;
        this.premiumRate = premiumInvoiceItemBuilder.premiumRate;
        this.premiumInvoice = premiumInvoiceItemBuilder.premiumInvoice;
        this.corpMemberBenefit = premiumInvoiceItemBuilder.corpMemberBenefit;
        this.amount = premiumInvoiceItemBuilder.amount;
    }


    @Override
    public Long getId() {
        return idPremiumInvoiceItem;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idPremiumInvoiceItem",idPremiumInvoiceItem)
                .add("invoiceDate", invoiceDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(invoiceDate))
                .add("amount", amount);

        if (premiumRate != null){
            premiumRate.addJson(builder);
        }

        if (corpMemberBenefit != null){
            corpMemberBenefit.addJson(builder);
        }

        if (premiumInvoice != null){
            premiumInvoice.addJson(builder);
        }

    }

    public PremiumInvoiceItem() {
    }

    public static class PremiumInvoiceItemBuilder{
        private final LocalDate invoiceDate;
        private final PremiumRate premiumRate;
        private final PremiumInvoice premiumInvoice;
        private final CorpMemberBenefit corpMemberBenefit;
        private final BigDecimal amount;

        public PremiumInvoiceItemBuilder(LocalDate invoiceDate,
                                         PremiumRate premiumRate,
                                         PremiumInvoice premiumInvoice,
                                         CorpMemberBenefit corpMemberBenefit,
                                         BigDecimal amount) {
            this.invoiceDate = invoiceDate;
            this.premiumRate = premiumRate;
            this.premiumInvoice = premiumInvoice;
            this.corpMemberBenefit = corpMemberBenefit;
            this.amount = amount;
        }

        public PremiumInvoiceItem build(){
            return new PremiumInvoiceItem(this);
        }
    }

    public Long getIdPremiumInvoiceItem() {
        return idPremiumInvoiceItem;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public PremiumRate getPremiumRate() {
        return premiumRate;
    }

    public PremiumInvoice getPremiumInvoice() {
        return premiumInvoice;
    }

    public CorpMemberBenefit getCorpMemberBenefit() {
        return corpMemberBenefit;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
