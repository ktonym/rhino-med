package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by akipkoech on 31/10/15.
 */
@Entity
public class CorpAnnivSuspension extends AbstractEntity implements EntityItem<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idAnnivSuspension;

    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn (name="idCorpAnniv")
    private CorpAnniv corpAnniv;

    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate startDate;

    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate endDate;

    private String reason;

//    @ManyToOne (cascade=CascadeType.ALL)
//    @JoinColumn (name = "username")
//    private User user;

   // static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");


    public CorpAnnivSuspension() {
    }

    public Integer getIdAnnivSuspension() {
        return idAnnivSuspension;
    }

    public void setIdAnnivSuspension(Integer idAnnivSuspension) {
        this.idAnnivSuspension = idAnnivSuspension;
    }

    public CorpAnniv getCorpAnniv() {
        return corpAnniv;
    }

    public void setCorpAnniv(CorpAnniv corpAnniv) {
        this.corpAnniv = corpAnniv;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    @Override
    public Integer getId() {
        return this.idAnnivSuspension;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idAnnivSusp", idAnnivSuspension)
                .add("startDate", startDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(startDate))
                .add("endDate", endDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(endDate))
                .add("reason", reason);
        if(this.getCorpAnniv()!=null){
            corpAnniv.addJson(builder);
        }
//        if(this.getUser()!=null){
//            user.addJson(builder);
//        }
    }
}
