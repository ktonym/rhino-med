package ke.co.rhino.uw.entity;

import sun.plugin.services.WIExplorerBrowserService;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by user on 27/03/2017.
 */
@Entity
public class QuotationItem extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idQuoteItem;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate quoteItemDate;
    //private BigDecimal unitPrice;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "IDQUOTATION", nullable = false)
    private Quotation quotation;
    @ManyToOne
    @JoinColumn(name = "IDPREMIUM_RATE", nullable = false)
    private PremiumRate premiumRate;
    private Long loadDiscountFactor;

    public QuotationItem() {
    }

    public QuotationItem(QuotationItemBuilder builder) {
        this.quotation = builder.quotation;
        this.idQuoteItem = builder.idQuoteItem;
        this.quoteItemDate = builder.quoteItemDate;
       // this.unitPrice = builder.unitPrice;
        this.quantity = builder.quantity;
        this.premiumRate = builder.premiumRate;
        this.loadDiscountFactor = builder.loadDiscountFactor;
    }

    public static class QuotationItemBuilder{
        private final Quotation quotation;
        private Long idQuoteItem;
        private LocalDate quoteItemDate;
        //private BigDecimal unitPrice;
        private Integer quantity;
        private PremiumRate premiumRate;
        private Long loadDiscountFactor;

        public QuotationItemBuilder(Quotation quotation) {
            this.quotation = quotation;
        }

        public QuotationItemBuilder idQuoteItem(Long idQuoteItem){
            this.idQuoteItem = idQuoteItem;
            return this;
        }

        public QuotationItemBuilder quoteItemDate(LocalDate quoteItemDate){
            this.quoteItemDate = quoteItemDate;
            return this;
        }

        /*public QuotationItemBuilder unitPrice(BigDecimal unitPrice){
            this.unitPrice = unitPrice;
            return this;
        }*/

        public QuotationItemBuilder quantity(Integer quantity){
            this.quantity = quantity;
            return this;
        }

        public QuotationItemBuilder premiumRate(PremiumRate premiumRate){
            this.premiumRate = premiumRate;
            return this;
        }

        public QuotationItemBuilder loadDiscountFactor(Long loadDiscountFactor){
            this.loadDiscountFactor = loadDiscountFactor;
            return this;
        }

        public QuotationItem build(){
            return new QuotationItem(this);
        }

    }

    public Long getIdQuoteItem() {
        return idQuoteItem;
    }

    public LocalDate getQuoteItemDate() {
        return quoteItemDate;
    }

    /*public BigDecimal getUnitPrice() {
        return unitPrice;
    }*/

    public Integer getQuantity() {
        return quantity;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public PremiumRate getPremiumRate() {
        return premiumRate;
    }

    public Long getLoadDiscountFactor() {
        return loadDiscountFactor;
    }

    @Override
    public Long getId() {
        return idQuoteItem;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idQuoteItem", idQuoteItem)
                .add("quoteItemDate", quoteItemDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(quoteItemDate))
                .add("loadDiscountFactor", loadDiscountFactor)
                //.add("unitPrice", unitPrice)
                .add("quantity", quantity);
        quotation.addJson(builder);
        premiumRate.addJson(builder);
    }
}
