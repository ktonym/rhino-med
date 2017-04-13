package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 09/11/2015.
 */
@Entity
//@IdClass(MemberAnnivId.class)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"idMember","idCorpAnniv"}))
public class MemberAnniversary extends AbstractEntity implements EntityItem<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idMemberAnniv;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idMember", referencedColumnName = "idMember", nullable = false)
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idCorpAnniv", referencedColumnName = "idCorpAnniv", nullable = false)
    private CorpAnniv corpAnniv;

    @OneToMany(mappedBy = "memberAnniv")
    private List<MemberSuspension> memberSuspensions;

    @OneToMany(mappedBy = "memberAnniv")
    private List<CorpMemberBenefit> benefits;

    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate inception;

    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate expiry;

    public MemberAnniversary() {
    }

    public MemberAnniversary(MemberAnniversaryBuilder memberAnniversaryBuilder) {
        this.idMemberAnniv = memberAnniversaryBuilder.idMemberAnniv;
        this.member = memberAnniversaryBuilder.member;
        this.corpAnniv = memberAnniversaryBuilder.corpAnniv;
        this.inception = memberAnniversaryBuilder.inception;
        this.expiry = memberAnniversaryBuilder.expiry;
    }

    @Override
    public Long getId() {
        return idMemberAnniv;
    }

    /*@Override
        public MemberAnnivId getId() {
            return new MemberAnnivId(member,corpAnniv);
        }
        */
    public static class MemberAnniversaryBuilder{

        private Long idMemberAnniv;
        private final Member member;
        private final CorpAnniv corpAnniv;
        private LocalDate inception;
        private LocalDate expiry;

        public MemberAnniversaryBuilder(Member member,CorpAnniv corpAnniv) {
            this.member = member;
            this.corpAnniv = corpAnniv;
        }

        public MemberAnniversaryBuilder idMemberAnniv(Long idMemberAnniv){
            this.idMemberAnniv = idMemberAnniv;
            return this;
        }

        public MemberAnniversaryBuilder inception(LocalDate inception){
            this.inception = inception;
            return this;
        }

        public MemberAnniversaryBuilder expiry(LocalDate expiry){
            this.expiry = expiry;
            return this;
        }

        public MemberAnniversary build(){
            return new MemberAnniversary(this);
        }

    }

    public CorpAnniv getCorpAnniv() {
        return corpAnniv;
    }

    public Member getMember() {
        return member;
    }

    public List<MemberSuspension> getMemberSuspensions() {
        return memberSuspensions;
    }

    public List<CorpMemberBenefit> getBenefits() {
        return benefits;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idMemberAnniv", idMemberAnniv)
                .add("memberInception",inception == null ? "" : DATE_FORMATTER_yyyyMMdd.format(inception))
                .add("memberExpiry", expiry == null ? "" : DATE_FORMATTER_yyyyMMdd.format(expiry));
        member.addJson(builder);
        corpAnniv.addJson(builder);
    }

    @Override
    public String toString() {
        return "MemberAnniversary{" +
                "member=" + member +
                ", corpAnniv=" + corpAnniv +
                ", inception=" + inception +
                ", expiry=" + expiry +
                '}';
    }
}
