package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by akipkoech on 27/01/2016.
 */
@Entity@Table(name = "CONTACT_PERSON")
public class ContactPerson extends AbstractEntity implements EntityItem<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContactPerson;
    private String firstName;
    private String surname;
    private String email;
    private String tel;
    private String jobTitle;
    @ManyToOne(cascade = CascadeType.ALL)
    private Corporate corporate;
    @Convert(converter=LocalDateTimePersistenceConverter.class)
    private LocalDateTime lastUpdate;

    public ContactPerson() {
    }

    public ContactPerson(ContactPersonBuilder contactPersonBuilder) {
        this.idContactPerson = contactPersonBuilder.idContactPerson;
        this.firstName = contactPersonBuilder.firstName;
        this.surname = contactPersonBuilder.surname;
        this.email = contactPersonBuilder.email;
        this.tel = contactPersonBuilder.tel;
        this.jobTitle = contactPersonBuilder.jobTitle;
        this.corporate = contactPersonBuilder.corporate;
        this.lastUpdate = contactPersonBuilder.lastUpdate;
    }

    public Long getIdContactPerson() {
        return idContactPerson;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Corporate getCorporate() {
        return corporate;
    }

    @Override
    public Long getId() {
        return idContactPerson;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idContactInfo", idContactPerson)
                .add("firstName", firstName)
                .add("surname", surname)
                .add("jobTitle", jobTitle)
                .add("email", email)
                .add("tel", tel)
                .add("lastUpdate", lastUpdate == null ? "" : DATE_FORMATTER_yyyyMMddHHmm.format(lastUpdate));
        corporate.addJson(builder);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setCorporate(Corporate corporate) {
        this.corporate = corporate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public static class ContactPersonBuilder{
        private  final Corporate corporate;
        private Long idContactPerson;
        private String firstName;
        private String surname;
        private String email;
        private String tel;
        private String jobTitle;
        private LocalDateTime lastUpdate;

        public ContactPersonBuilder(Corporate corporate) {
            this.corporate = corporate;
            this.firstName = firstName;
            this.surname = surname;
        }
        public ContactPersonBuilder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public ContactPersonBuilder surname(String surname){
            this.surname = surname;
            return this;
        }


        public ContactPersonBuilder idContactPerson(Long idContactPerson){
            this.idContactPerson = idContactPerson;
            return this;
        }

        public ContactPersonBuilder email(String email){
            this.email = email;
            return this;
        }

        public ContactPersonBuilder tel(String tel){
            this.tel = tel;
            return this;
        }

        public ContactPersonBuilder jobTitle(String jobTitle){
            this.jobTitle = jobTitle;
            return this;
        }

        public ContactPersonBuilder lastUpdate(LocalDateTime lastUpdate){
            this.lastUpdate = lastUpdate;
            return this;
        }

        public ContactPerson build(){
            return new ContactPerson(this);
        }
    }

}
