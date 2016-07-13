package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by akipkoech on 12/9/14.
 */
@Entity
public class CorpInvoice extends AbstractEntity implements EntityItem<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCorpInvoice;
    private String invoiceNo;
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate invoiceDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "anniv_id")
    private CorpAnniv anniv;

    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    // TODO consider the various types of invoices: new, addition, etc


    public CorpInvoice() {
    }

    public Integer getIdCorpInvoice() {
        return idCorpInvoice;
    }

    public void setIdCorpInvoice(Integer idCorpInvoice) {
        this.idCorpInvoice = idCorpInvoice;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public CorpAnniv getAnniv() {
        return anniv;
    }

    public void setAnniv(CorpAnniv anniv) {
        this.anniv = anniv;
    }

    @Override
    public Integer getId() {
        return idCorpInvoice;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idCorpInvoice",idCorpInvoice)
                .add("invoiceNo",invoiceNo)
                .add("invoiceDate", invoiceDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(invoiceDate) );
        anniv.addJson(builder);
    }
}
