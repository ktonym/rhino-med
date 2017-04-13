package ke.co.rhino.uw.entity;

import ke.co.rhino.fin.entity.FundInvoice;
import ke.co.rhino.fin.entity.PremiumInvoice;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * EntityItem implementation class for Entity: CorpBenefit
 *
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"BENEFIT_CODE","CAT_ID"})})
public class CorpBenefit extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCorpBenefit;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BENEFIT_CODE", nullable = false)
	private BenefitRef benefitRef;
	private BigDecimal upperLimit;
    @Enumerated(EnumType.STRING)
	private MemberType memberType;
    @Enumerated(EnumType.STRING)
    private BenefitType benefitType;
	private boolean sharing;
    private boolean requiresPreAuth;
    private Integer waitingPeriod;
	private static final long serialVersionUID = 1L;
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="parentBenefit_id",nullable = true)
	private CorpBenefit parentBenefit;
	@OneToMany(mappedBy = "parentBenefit")
	private List<CorpBenefit> subBenefits;
    @OneToMany(mappedBy = "benefit")
    private List<CorpMemberBenefit> corpMemberBenefits;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CAT_ID",nullable = false)
    private Category category;
    //The following two fields are optional
    @OneToOne(mappedBy = "benefit")
    private PremiumInvoice premiumInvoice;
    @OneToOne(mappedBy = "benefit")
    private FundInvoice fundInvoice;
//    @OneToMany(mappedBy = "corpBenefit")
//    private  List<PremiumRate> premiumRates;
//TODO consider linking the rate sheet to this class

    public CorpBenefit() {
    }

    public CorpBenefit(CorpBenefitBuilder corpBenefitBuilder) {
        this.benefitRef = corpBenefitBuilder.benefitRef;
        this.upperLimit = corpBenefitBuilder.upperLimit;
        this.memberType = corpBenefitBuilder.memberType;
        this.benefitType = corpBenefitBuilder.benefitType;
        this.sharing = corpBenefitBuilder.sharing;
        this.requiresPreAuth = corpBenefitBuilder.requiresPreAuth;
        this.parentBenefit = corpBenefitBuilder.parentBenefit;
        this.category = corpBenefitBuilder.category;
        this.waitingPeriod = corpBenefitBuilder.waitingPeriod;

    }

    public static class CorpBenefitBuilder {
        private final BenefitRef benefitRef;
        private final BigDecimal upperLimit;
        private final MemberType memberType;
        private final BenefitType benefitType;
        private final Category category;
        private CorpBenefit parentBenefit;
        private boolean sharing;
        private boolean requiresPreAuth;
        private Integer waitingPeriod;

        public CorpBenefitBuilder(BenefitRef benefitRef, BigDecimal upperLimit, MemberType memberType, BenefitType benefitType, Category category) {
            this.benefitRef = benefitRef;
            this.upperLimit = upperLimit;
            this.memberType = memberType;
            this.benefitType = benefitType;
            this.category = category;
        }

        public CorpBenefitBuilder sharing(boolean sharing) {
            this.sharing = sharing;
            return this;
        }

        public CorpBenefitBuilder requiresPreAuth(boolean requiresPreAuth) {
            this.requiresPreAuth = requiresPreAuth;
            return this;
        }

        public CorpBenefitBuilder parentBenefit(CorpBenefit parentBenefit){
            this.parentBenefit = parentBenefit;
            return this;
        }

        public CorpBenefitBuilder waitingPeriod(Integer waitingPeriod){
            this.waitingPeriod = waitingPeriod;
            return this;
        }

        public CorpBenefit build(){
            return new CorpBenefit(this);
        }

    }

    public Long getIdCorpBenefit() {
        return idCorpBenefit;
    }

    public BenefitRef getBenefitRef() {
        return benefitRef;
    }

    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    public MemberType getMemberType() {
        return memberType;
    }

    public boolean isSharing() {
        return sharing;
    }

    public boolean isRequiresPreAuth() {
        return requiresPreAuth;
    }

    public Integer getWaitingPeriod() {
        return waitingPeriod;
    }

    public CorpBenefit getParentBenefit() {
        return parentBenefit;
    }

    public List<CorpBenefit> getSubBenefits() {
        return subBenefits;
    }

    public List<CorpMemberBenefit> getCorpMemberBenefits() {
        return corpMemberBenefits;
    }

    public Category getCategory() {
        return category;
    }

//    public List<PremiumRate> getPremiumRates() {
//        return premiumRates;
//    }
//
//    public void setPremiumRates(List<PremiumRate> premiumRates) {
//        this.premiumRates = premiumRates;
//    }

    public boolean isMainBenefit(){
        return (this.getParentBenefit() == null);
    }

    public BenefitType getBenefitType() {
        return benefitType;
    }

    @Override
    public Long getId() {
        return idCorpBenefit;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idCorpBenefit",idCorpBenefit)
                .add("upperLimit",upperLimit)
                .add("memberType",memberType.toString())
                .add("benefitType",benefitType.toString())
                .add("sharing", sharing)
                .add("requiresPreAuth",requiresPreAuth)
                .add("waitingPeriod", waitingPeriod);
        if(!isMainBenefit()) {
            parentBenefit.addJson(builder);
        }
        if(category!=null){
            category.addJson(builder);
        }
        if(benefitRef!=null){
            benefitRef.addJson(builder);
        }
    }
}
