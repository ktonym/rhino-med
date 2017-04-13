package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by user on 27/03/2017.
 */
@Entity
//@EnableJpaAuditing
public class Quotation extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idQuotation;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate quotationDate;
    @OneToMany(mappedBy = "quotation")
    private List<QuotationItem> quotationItems;
    @ManyToOne
    private Corporate corporate;
    /*private boolean deleted;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate deletedDate;*/

    public Quotation() {
    }

    public Quotation(QuotationBuilder builder) {
        this.idQuotation = builder.idQuotation;
        this.quotationDate = builder.quotationDate;
        this.corporate = builder.corporate;
    }

    public static class QuotationBuilder{
        private Long idQuotation;
        private LocalDate quotationDate;
        private final Corporate corporate;
        /*private boolean deleted;
        private LocalDate deletedDate;*/

        public QuotationBuilder(Corporate corporate) {
            this.corporate = corporate;
        }

        public QuotationBuilder idQuotation(Long idQuotation){
            this.idQuotation = idQuotation;
            return this;
        }

        public QuotationBuilder quotationDate(LocalDate quotationDate){
            this.quotationDate = quotationDate;
            return this;
        }

        /*public QuotationBuilder deleted(boolean deleted){
            this.deleted = deleted;
            return this;
        }*/

        public Quotation build(){
            return new Quotation(this);
        }

    }

    public Long getIdQuotation() {
        return idQuotation;
    }

    public LocalDate getQuotationDate() {
        return quotationDate;
    }

    public List<QuotationItem> getQuotationItems() {
        return quotationItems;
    }

    public Corporate getCorporate() {
        return corporate;
    }

    @Override
    public Long getId() {
        return idQuotation;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idQuotation", idQuotation)
                .add("quotationDate", quotationDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(quotationDate));
        corporate.addJson(builder);
    }
}
