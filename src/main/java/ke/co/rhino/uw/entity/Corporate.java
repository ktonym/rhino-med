package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by akipkoech on 27/01/2016.
 */
@Entity@Table(name = "CORPORATE")
public class Corporate extends AbstractEntity implements EntityItem<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCorporate;
    private String name;
    private String abbreviation;
    private String pin;
    private String tel;
    private String email;
    private String postalAddress;
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate joined;
    @OneToMany(mappedBy = "corporate")
    private List<Member> members;
    @OneToMany(mappedBy = "corporate")
    private List<CorpAnniv> annivs;
    @OneToMany(mappedBy = "corporate")
    private List<ContactPerson> contactPersons;
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime lastUpdate;
    @OneToMany(mappedBy = "corporate")
    private List<GroupRate> rates;
    @Enumerated(EnumType.STRING)
    private PlanType planType;

    @OneToMany(mappedBy = "corporate")
    private List<Quotation> quotations;

    public Corporate() {
    }

    private Corporate(CorporateBuilder builder) {
        this.name = builder.name;
        this.abbreviation = builder.abbreviation;
        this.tel = builder.tel;
        this.email = builder.email;
        this.joined = builder.joined;
        this.postalAddress = builder.postalAddress;
        this.lastUpdate = builder.lastUpdate;
        this.idCorporate = builder.idCorporate;
        this.pin = builder.pin;
        this.planType = builder.planType;
    }

    @Override
    public Long getId() {
        return idCorporate;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idCorporate", idCorporate)
                .add("name", name)
                .add("abbreviation", abbreviation)
                .add("pin", pin == null ? "" : pin)
                .add("tel", tel == null ? "" : tel)
                .add("email",email)
                .add("postalAddress", postalAddress == null ? "" : postalAddress)
                .add("joined", joined == null ? "" : DATE_FORMATTER_yyyyMMdd.format(joined))
                .add("lastUpdate", lastUpdate == null ? "" : DATE_FORMATTER_yyyyMMddHHmm.format(lastUpdate))
                .add("planType", planType.toString());
    }

    public static class CorporateBuilder{
        private final String name;
        private final String abbreviation;
        private Long idCorporate;
        private String pin;
        private String tel;
        private String email;
        private String postalAddress;
        private LocalDate joined;
        private LocalDateTime lastUpdate;
        private PlanType planType;

        public CorporateBuilder(String name, String abbreviation) {
            this.name = name;
            this.abbreviation = abbreviation;
        }

        public CorporateBuilder idCorporate(Long idCorporate){
            this.idCorporate = idCorporate;
            return this;
        }

        public CorporateBuilder pin(String pin){
            this.pin = pin;
            return this;
        }

        public CorporateBuilder tel(String tel){
            this.tel = tel;
            return this;
        }

        public CorporateBuilder email(String email){
            this.email = email;
            return this;
        }

        public CorporateBuilder postalAddress(String postalAddress){
            this.postalAddress = postalAddress;
            return this;
        }

        public CorporateBuilder joined(LocalDate joined){
            this.joined = joined;
            return this;
        }

        public CorporateBuilder lastUpdate(LocalDateTime lastUpdate){
            this.lastUpdate = lastUpdate;
            return this;
        }

        public CorporateBuilder planType(PlanType planType){
            this.planType = planType;
            return this;
        }

        public Corporate build(){
            return new Corporate(this);
        }

    }

    public Long getIdCorporate() {
        return idCorporate;
    }

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public LocalDate getJoined() {
        return joined;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public List<GroupRate> getRates() {
        return rates;
    }

    public List<Member> getMembers() {
        return members;
    }

    public String getPin(){
        return pin;
    }

    public List<CorpAnniv> getAnnivs() {
        return annivs;
    }

    public List<ContactPerson> getContactPersons() {
        return contactPersons;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public List<Quotation> getQuotations() {
        return quotations;
    }
}
