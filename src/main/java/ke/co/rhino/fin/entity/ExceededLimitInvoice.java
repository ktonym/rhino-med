package ke.co.rhino.fin.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by akipkoech on 08/07/2016.
 */
@Entity
public class ExceededLimitInvoice extends AbstractEntity implements EntityItem<Long> {

    @Id
    private Long idXLInvoice;
    @ManyToOne(optional = false)
    private CorpMemberBenefit corpMemberBenefit;
    private BigDecimal amount;
    private LocalDate createdDate;
    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public ExceededLimitInvoice() {
    }

    public ExceededLimitInvoice(ExceededLimitInvoiceBuilder builder) {
        this.idXLInvoice = builder.idXLInvoice;
        this.corpMemberBenefit = builder.corpMemberBenefit;
        this.amount = builder.amount;
        this.createdDate = builder.createdDate;
    }

    public static class ExceededLimitInvoiceBuilder{

        private Long idXLInvoice;
        private final CorpMemberBenefit corpMemberBenefit;
        private final BigDecimal amount;
        private final LocalDate createdDate;


        public ExceededLimitInvoiceBuilder(CorpMemberBenefit corpMemberBenefit, BigDecimal amount, LocalDate createdDate) {
            this.corpMemberBenefit = corpMemberBenefit;
            this.amount = amount;
            this.createdDate = createdDate;
        }

        public ExceededLimitInvoiceBuilder idXLInvoice(Long idXLInvoice){
            this.idXLInvoice = idXLInvoice;
            return this;
        }

        public ExceededLimitInvoice build(){
            return new ExceededLimitInvoice(this);
        }

    }

    public Long getIdXLInvoice() {
        return idXLInvoice;
    }

    public CorpMemberBenefit getCorpMemberBenefit() {
        return corpMemberBenefit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    @Override
    public Long getId() {
        return idXLInvoice;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        corpMemberBenefit.addJson(builder);
        builder.add("idXLInvoice",idXLInvoice)
                .add("amount",amount)
                .add("createdDate",createdDate==null?"":DATE_FORMATTER_yyyyMMdd.format(createdDate));

    }
}
