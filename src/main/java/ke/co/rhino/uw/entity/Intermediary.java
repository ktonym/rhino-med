package ke.co.rhino.uw.entity;

import org.springframework.cglib.core.Local;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.IntBinaryOperator;

/**
 * Created by akipkoech on 12/8/14.
 */
@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="INTERMEDIARY_TYPE",discriminatorType = DiscriminatorType.STRING)
//TODO consider replacing above @nnotation with   @DiscriminatorFormula
//TODO read more about @ForceDiscriminator
@Table(name = "INTERMEDIARY")
public class Intermediary extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idIntermediary;
    private String pin;
   // @Column(name = "INTERMEDIARY_TYPE",insertable = false,updatable = false)
    private IntermediaryType type;
    private String name;
    private String street;
    private String town;
    private String postalAddress;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate joinDate;
    private String email;
    private String tel;
    @OneToMany(mappedBy = "intermediary")
    private List<CorpAnniv> corpAnnivs;
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime lastUpdate;


    public Intermediary() {
    }

    public Intermediary(IntermediaryBuilder builder) {
        this.name = builder.name;
        this.type = builder.type;
        this.pin = builder.pin;
        this.joinDate = builder.joinDate;
        this.street = builder.street;
        this.town = builder.town;
        this.postalAddress = builder.postalAddress;
        this.email = builder.email;
        this.tel = builder.tel;
        this.lastUpdate = builder.lastUpdate;
        this.idIntermediary = builder.idIntermediary; //TODO check whether this always works -- hint: NPE
       // this.corpAnnivs.add(builder.corpAnniv);
    }

    public static class IntermediaryBuilder{
        private final String name;
        private final String pin;
        private final IntermediaryType type;
        private final LocalDate joinDate;
        private String street;
        private String town;
        private String postalAddress;
        private String email;
        private String tel;
        private LocalDateTime lastUpdate;
        private Long idIntermediary;
        //private CorpAnniv corpAnniv;

        public IntermediaryBuilder(String name, String pin, IntermediaryType type, LocalDate joinDate) {
            this.name = name;
            this.pin = pin;
            this.type = type;
            this.joinDate = joinDate;
        }

        public IntermediaryBuilder idIntermediary(Long idIntermediary){
            this.idIntermediary = idIntermediary;
            return this;
        }

        public IntermediaryBuilder street(String street){
            this.street = street;
            return this;
        }

        public IntermediaryBuilder town(String town){
            this.town = town;
            return this;
        }

        public IntermediaryBuilder postalAddress(String postalAddress){
            this.postalAddress = postalAddress;
            return this;
        }

        public IntermediaryBuilder email(String email){
            this.email = email;
            return this;
        }

        public IntermediaryBuilder tel(String tel){
            this.tel = tel;
            return this;
        }

//        public IntermediaryBuilder corpAnniv(CorpAnniv anniv){
//            this.corpAnniv = anniv;
//            return this;
//        }

        public IntermediaryBuilder lastUpdate(LocalDateTime lastUpdate){
            this.lastUpdate = lastUpdate;
            return this;
        }

        public Intermediary build(){
            return new Intermediary(this);
        }

    }

    public Long getIdIntermediary() {
        return idIntermediary;
    }

    public String getPin() {
        return pin;
    }

    public IntermediaryType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public List<CorpAnniv> getCorpAnnivs() {
        return corpAnnivs;
    }

    public LocalDateTime getLastUpdate(){
        return lastUpdate;
    }

    @Override
    public Long getId() {
        return idIntermediary;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idIntermediary", idIntermediary)
                .add("pin", pin)
                .add("type",type.toString())
                .add("name", name)
                .add("street",street)
                .add("town", town)
                .add("postalAddress", postalAddress == null ? "" : postalAddress)
                .add("joined", joinDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(joinDate))
                .add("email",email)
                .add("tel",tel)
                .add("lastUpdate", lastUpdate == null ? "" : DATE_FORMATTER_yyyyMMddHHmm.format(lastUpdate));
    }

    @Override
    public String toString() {
        return "Intermediary{" +
                "idIntermediary=" + idIntermediary +
                ", pin='" + pin + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", town='" + town + '\'' +
                ", postalAddress='" + postalAddress + '\'' +
                ", joinDate=" + joinDate +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }
}
