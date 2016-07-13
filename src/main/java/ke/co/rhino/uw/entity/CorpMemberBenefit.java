package ke.co.rhino.uw.entity;

import ke.co.rhino.care.entity.PreAuth;
import ke.co.rhino.claim.entity.Bill;
import ke.co.rhino.fin.entity.PremiumInvoiceItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 12/8/14.
 */
@Entity @IdClass(CorpMemberBenefitId.class)
public class CorpMemberBenefit extends AbstractEntity {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({@JoinColumn(name = "idMember", referencedColumnName = "idMember", updatable = false, insertable = false),
            @JoinColumn(name = "idCorpAnniv", referencedColumnName = "idCorpAnniv", updatable = false, insertable = false)})
    private MemberAnniversary memberAnniv;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="idCorpBenefit", referencedColumnName = "idCorpBenefit")
    private CorpBenefit benefit;
    private BenefitStatus status;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate wef;
    //To handle parent-child relations
    @ManyToOne
    private CorpMemberBenefit parentMemberBenefit;
    @OneToMany(mappedBy = "parentMemberBenefit")
    private List<CorpMemberBenefit> childMemberBenefits;

    /**
     * The following fields to be accessed via http in future: micro services
     */
    @OneToMany(mappedBy = "corpMemberBenefit")
    private List<PreAuth> preAuthList;
    @OneToOne(mappedBy = "corpMemberBenefit",optional = true) // optional for self-funded benefits
    private PremiumInvoiceItem invoiceItem;
    @OneToMany(mappedBy = "corpMemberBenefit")
    private List<Bill> bills;


    public CorpMemberBenefit() {
    }

    public CorpMemberBenefit(CorpMemberBenefitBuilder corpMemberBenefitBuilder) {
        this.wef = corpMemberBenefitBuilder.wef;
        this.status = corpMemberBenefitBuilder.status;
        this.benefit = corpMemberBenefitBuilder.benefit;
        this.memberAnniv = corpMemberBenefitBuilder.memberAnniv;
        this.parentMemberBenefit = corpMemberBenefitBuilder.parentMemberBenefit;
    }


    public static class CorpMemberBenefitBuilder{
        private final MemberAnniversary memberAnniv;
        private final CorpBenefit benefit;
        private BenefitStatus status;
        private LocalDate wef;
        private CorpMemberBenefit parentMemberBenefit;

        public CorpMemberBenefitBuilder(MemberAnniversary memberAnniv, CorpBenefit benefit) {
            this.memberAnniv = memberAnniv;
            this.benefit = benefit;
        }

        public CorpMemberBenefitBuilder status(BenefitStatus status){
            this.status= status;
            return this;
        }

        public CorpMemberBenefitBuilder wef(LocalDate wef){
            this.wef = wef;
            return this;
        }

        public CorpMemberBenefitBuilder parentMemberBenefit(CorpMemberBenefit parentMemberBenefit){
            this.parentMemberBenefit = parentMemberBenefit;
            return this;
        }

        public CorpMemberBenefit build(){
            return new CorpMemberBenefit(this);
        }

    }

    public MemberAnniversary getMemberAnniv() {
        return memberAnniv;
    }

    public CorpBenefit getBenefit() {
        return benefit;
    }

    public BenefitStatus getStatus() {
        return status;
    }

    public LocalDate getWef() {
        return wef;
    }

    public CorpMemberBenefit getParentMemberBenefit() {
        return parentMemberBenefit;
    }

    public List<CorpMemberBenefit> getChildMemberBenefits() {
        return childMemberBenefits;
    }

    public List<PreAuth> getPreAuthList() {
        return preAuthList;
    }

    public PremiumInvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public boolean isMainBenefit(){
        return this.parentMemberBenefit == null;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder.add("status",status.toString())
                .add("wef", wef == null ? "" : DATE_FORMATTER_yyyyMMdd.format(wef));
        memberAnniv.addJson(builder);
        benefit.addJson(builder);

    }
}
