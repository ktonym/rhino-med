package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by akipkoech on 12/8/14.
 */
@Entity
public class Principal extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPrincipal;
    @Column(unique = true)
    private String familyNo;
    private String firstName;
    private String surname;
    private String otherNames;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dob;
    @OneToMany(mappedBy = "principal")
    private List<Member> dependants;
    @OneToMany(mappedBy = "principal")
    private List<CategoryPrincipal> categoryPrincipals;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCorporate",nullable = false)
    private Corporate corporate;

    public Principal() {
    }

    public Long getIdPrincipal() {
        return idPrincipal;
    }

    public void setIdPrincipal(Long idPrincipal) {
        this.idPrincipal = idPrincipal;
    }

    public List<CategoryPrincipal> getCategoryPrincipals() {
        return categoryPrincipals;
    }

    public void setCategoryPrincipals(List<CategoryPrincipal> categoryPrincipals) {
        this.categoryPrincipals = categoryPrincipals;
    }

    public String getFamilyNo() {
        return familyNo;
    }

    public void setFamilyNo(String familyNo) {
        this.familyNo = familyNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public List<Member> getDependants() {
        return dependants;
    }

    public void setDependants(List<Member> dependants) {
        this.dependants = dependants;
    }

    public Corporate getCorporate() {
        return corporate;
    }

    public void setCorporate(Corporate corporate) {
        this.corporate = corporate;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("Principal name: ")
                .append(firstName)
                .append(" ").append(surname).append(" ").append(otherNames);

        return builder.toString();
    }

    @Override
    public Long getId() {
        return idPrincipal;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder.add("idPrincipal", idPrincipal)
                .add("familyNo", familyNo)
                .add("firstName", firstName)
                .add("surname", surname)
                .add("otherNames", otherNames == null ? "" : otherNames)
                .add("dob", dob == null ? "" : DATE_FORMATTER_yyyyMMdd.format(dob))
                .add("fullName", firstName.concat(" ").concat(surname).concat(" ").concat(otherNames==null?"":otherNames));

        if(corporate!=null){
            corporate.addJson(builder);
        }
    }
}
