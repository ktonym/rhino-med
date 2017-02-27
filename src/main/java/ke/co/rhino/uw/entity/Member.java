package ke.co.rhino.uw.entity;

import ke.co.rhino.claim.entity.Assessment;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by akipkoech on 12/8/14.
 */
@Entity
public class Member extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMember;
    @Column(unique = true)
    private String memberNo;
    private String firstName;
    private String surname;
    private String otherNames;
    private Sex sex;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate dob;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idPrincipal")
    private Member principal;
    @OneToMany(mappedBy = "principal")
    private List<Member> dependants;
    @Enumerated(EnumType.STRING)
    private MemberType memberType;
    @OneToMany(mappedBy = "member")
    private List<MemberAnniversary> memberAnniversaries;
    /*@OneToMany(mappedBy = "member")
    private List<Assessment> assessments;*/
    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCorporate",nullable = false)
    private Corporate corporate;
    @OneToMany(mappedBy = "member")
    private List<MemberCategory> memberCategories;

    static final DateTimeFormatter DATE_FORMATTER_yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");

    public Member() {
    }

    public Member(MemberBuilder memberBuilder){
        this.firstName = memberBuilder.firstName;
        this.surname = memberBuilder.surname;
        this.otherNames = memberBuilder.otherNames;
        this.memberNo = memberBuilder.memberNo;
        this.sex = memberBuilder.sex;
        this.dob = memberBuilder.dob;
        this.principal = memberBuilder.principal;
        this.memberType = memberBuilder.memberType;
        this.corporate = memberBuilder.corporate;
    }

    public static class MemberBuilder{
        private final String firstName;
        private final String surname;
        private String otherNames;
        private String memberNo;
        private final Sex sex;
        private final LocalDate dob;
        private Member principal;
        private final MemberType memberType;
        private final Corporate corporate;

        public MemberBuilder(String firstName, String surname,Sex sex,
                             LocalDate dob, MemberType memberType, Corporate corporate) {
            this.firstName = firstName;
            this.surname = surname;
            this.sex = sex;
            this.dob = dob;
            this.memberType = memberType;
            this.corporate = corporate;
        }

        public MemberBuilder memberNo(String memberNo){
            this.memberNo = memberNo;
            return this;
        }

        public MemberBuilder principal(Member principal){
            this.principal = principal;
            return  this;
        }

        public MemberBuilder otherNames(String otherNames){
            this.otherNames = otherNames;
            return this;
        }

        public Member build(){
            return new Member(this);
        }

    }

    public Long getIdMember() {
        return idMember;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public Sex getSex() {
        return sex;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Member getPrincipal() {
        return principal;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public List<MemberAnniversary> getMemberAnniversaries() {
        return memberAnniversaries;
    }

    public List<Member> getDependants() {
        return dependants;
    }

    public Corporate getCorporate() {
        return corporate;
    }

    public List<MemberCategory> getMemberCategories() {
        return memberCategories;
    }

    /*public List<Assessment> getAssessments(){
                return assessments;
            }
            */
    @Override
    public Long getId() {
        return idMember;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
         builder.add("idMember",idMember)
                 .add("memberNo",memberNo)
                 .add("firstName",firstName)
                 .add("surname",surname)
                 .add("otherNames",otherNames == null ? "" : otherNames)
                 .add("sex",sex.toString())
                 .add("dob", dob == null ? "" : DATE_FORMATTER_yyyyMMdd.format(dob))
                 .add("memberType", memberType.toString())
                 .add("fullName", firstName.concat(" ").concat(surname).concat(" ").concat(otherNames==null?"":otherNames));
        if(principal!=null) {
            /*builder.add("idPrincipal",principal.getId())
                    .add("")*/
            principal.addJson(builder);
        }

        if(corporate!=null){
            corporate.addJson(builder);
        }

    }
}
