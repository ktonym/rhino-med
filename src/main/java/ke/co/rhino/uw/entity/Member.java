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
    @JoinColumn(name="principal_id",nullable = false)
    private Principal principal;
    private MemberType memberType;
    @OneToMany(mappedBy = "member")
    private List<MemberAnniversary> memberAnniversaries;

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
    }

    public static class MemberBuilder{
        private final String firstName;
        private final String surname;
        private String otherNames;
        private final String memberNo;
        private final Sex sex;
        private final LocalDate dob;
        private Principal principal;
        private final MemberType memberType;

        public MemberBuilder(String firstName, String surname, String memberNo, Sex sex, LocalDate dob, MemberType memberType) {
            this.firstName = firstName;
            this.surname = surname;
            this.memberNo = memberNo;
            this.sex = sex;
            this.dob = dob;
            this.memberType = memberType;
        }

        public MemberBuilder principal(Principal principal){
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

    public Principal getPrincipal() {
        return principal;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public List<MemberAnniversary> getMemberAnniversaries() {
        return memberAnniversaries;
    }

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
                 .add("otherNames",otherNames)
                 .add("sex",sex.toString())
                 .add("dob", dob == null ? "" : DATE_FORMATTER_yyyyMMdd.format(dob))
                 .add("memberType", memberType.toString());
        if(principal!=null) {
            principal.addJson(builder);
        }
    }
}
