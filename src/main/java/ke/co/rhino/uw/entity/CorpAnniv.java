package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by akipkoech on 27/01/2016.
 */
@Entity@Table(name = "CORP_ANNIV")
public class CorpAnniv extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCorpAnniv;
    private Integer anniv;
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate inception;
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate expiry;
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate renewalDate;
    @OneToMany(mappedBy = "corpAnniv")
    private List<Category> category;
    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name="corp_id")
    private Corporate corporate;
    @ManyToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "intermediary_id",referencedColumnName = "idIntermediary",nullable = true)
    private Intermediary intermediary;
    @OneToMany(mappedBy = "corpAnniv")
    private List<MemberAnniversary> memberAnniversaries;
    @OneToMany(mappedBy = "corpAnniv")
    private List<CorpAnnivSuspension> annivSuspensions;
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime lastUpdate;


    public CorpAnniv() {
    }

    public CorpAnniv(CorpAnnivBuilder corpAnnivBuilder){
        this.anniv = corpAnnivBuilder.anniv;
        this.inception = corpAnnivBuilder.inception;
        this.expiry = corpAnnivBuilder.expiry;
        this.renewalDate = corpAnnivBuilder.renewal;
        this.corporate = corpAnnivBuilder.corporate;
        this.intermediary = corpAnnivBuilder.intermediary;
        this.lastUpdate = corpAnnivBuilder.lastUpdate;
    }

    public static class CorpAnnivBuilder {
        private final Integer anniv;
        private final LocalDate inception;
        private final Corporate corporate;
        private LocalDate expiry;
        private LocalDate renewal;
        private Intermediary intermediary;
        private LocalDateTime lastUpdate;

        public CorpAnnivBuilder(Integer anniv, LocalDate inception, Corporate corporate) {
            this.anniv = anniv;
            this.inception = inception;
            this.corporate = corporate;
        }

        public CorpAnnivBuilder expiry(LocalDate expiry){
            this.expiry = expiry;
            return this;
        }

        public CorpAnnivBuilder renewal(LocalDate renewal){
            this.renewal = renewal;
            return this;
        }

        public CorpAnnivBuilder intermediary(Intermediary intermediary){
            this.intermediary = intermediary;
            return this;
        }

        public CorpAnnivBuilder lastUpdate(LocalDateTime lastUpdate){
            this.lastUpdate = lastUpdate;
            return this;
        }

        public CorpAnniv build(){
            return new CorpAnniv(this);
        }

    }

    public Long getIdCorpAnniv() {
        return idCorpAnniv;
    }

    public Integer getAnniv() {
        return anniv;
    }

    public LocalDate getInception() {
        return inception;
    }

    public LocalDate getExpiry() {
        return expiry;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public List<Category> getCategory() {
        return category;
    }

    public Corporate getCorporate() {
        return corporate;
    }

    public Intermediary getIntermediary() {
        return intermediary;
    }

    public List<MemberAnniversary> getMemberAnniversaries() {
        return memberAnniversaries;
    }

    public List<CorpAnnivSuspension> getAnnivSuspensions() {
        return annivSuspensions;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setAnniv(Integer anniv) {
        this.anniv = anniv;
    }

    public void setInception(LocalDate inception) {
        this.inception = inception;
    }

    public void setExpiry(LocalDate expiry) {
        this.expiry = expiry;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public void setCorporate(Corporate corporate) {
        this.corporate = corporate;
    }

    public void setIntermediary(Intermediary intermediary) {
        this.intermediary = intermediary;
    }

    public void setMemberAnniversaries(List<MemberAnniversary> memberAnniversaries) {
        this.memberAnniversaries = memberAnniversaries;
    }

    public void setAnnivSuspensions(List<CorpAnnivSuspension> annivSuspensions) {
        this.annivSuspensions = annivSuspensions;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public Long getId() {
        return idCorpAnniv;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idCorpAnniv", idCorpAnniv)
                .add("anniv", anniv)
                .add("inception", inception == null ? "" : DATE_FORMATTER_yyyyMMdd.format(inception))
                .add("expiry", expiry == null ? "" : DATE_FORMATTER_yyyyMMdd.format(expiry))
                .add("renewalDate", renewalDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(renewalDate))
                .add("lastUpdate", lastUpdate == null ? "" : DATE_FORMATTER_yyyyMMddHHmm.format(lastUpdate));
        if(this.getCorporate()!=null){
            corporate.addJson(builder);
        }
        if(this.getIntermediary()!= null){
            intermediary.addJson(builder);
        }
    }

}
