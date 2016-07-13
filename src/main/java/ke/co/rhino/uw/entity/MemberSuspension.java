package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by akipkoech on 09/11/2015.
 */
@Entity
public class MemberSuspension extends AbstractEntity implements EntityItem<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idMemberSuspension;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate effectiveDate;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate reinstatementDate;
    private String reason;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({@JoinColumn(name = "idMember", referencedColumnName = "idMember", updatable = false, insertable = false),
            @JoinColumn(name = "idCorpAnniv", referencedColumnName = "idCorpAnniv", updatable = false, insertable = false)})
    private MemberAnniversary memberAnniv;

    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public MemberSuspension() {
    }

    public Integer getIdMemberSuspension() {
        return idMemberSuspension;
    }

    public void setIdMemberSuspension(Integer idMemberSuspension) {
        this.idMemberSuspension = idMemberSuspension;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalDate getReinstatementDate() {
        return reinstatementDate;
    }

    public void setReinstatementDate(LocalDate reinstatementDate) {
        this.reinstatementDate = reinstatementDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public MemberAnniversary getMemberAnniv() {
        return memberAnniv;
    }

    public void setMemberAnniv(MemberAnniversary memberAnniv) {
        this.memberAnniv = memberAnniv;
    }

    @Override
    public Integer getId() {
        return idMemberSuspension;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idMemberSuspension", idMemberSuspension)
                .add("effectiveDate", effectiveDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(effectiveDate))
                .add("reinstatementDate", reinstatementDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(reinstatementDate))
                .add("reinstated", reinstatementDate == null ? "N" : "Y")
                .add("reason", reason);
        if(memberAnniv!=null){
            memberAnniv.addJson(builder);
        }
    }
}
