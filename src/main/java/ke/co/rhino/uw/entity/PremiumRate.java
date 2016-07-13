package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by akipkoech on 12/9/14.
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="PREMIUM_TYPE",discriminatorType = DiscriminatorType.STRING)
public class PremiumRate extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPremiumRate;
    @Column(name = "PREMIUM_TYPE",insertable = false,updatable = false)
    private PremiumType premiumType;
    @Column(name = "LIMIT")
    private BigDecimal upperLimit;
    @Column(name = "PREMIUM")
    private BigDecimal premium;
    @Column(name = "FAM_SIZE")
    private String familySize;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BENEFIT_CODE",nullable = false)
    private BenefitRef benefitRef;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "idCorporate",nullable = false)
//    private Corporate corporate;

    public PremiumRate() {
    }

    public Long getIdPremiumRate() {
        return idPremiumRate;
    }

    public void setIdPremiumRate(Long idPremiumRate) {
        this.idPremiumRate = idPremiumRate;
    }

    public PremiumType getPremiumType() {
        return premiumType;
    }

    public void setPremiumType(PremiumType premiumType) {
        this.premiumType = premiumType;
    }

    public BenefitRef getBenefitRef() {
        return benefitRef;
    }

    public void setBenefitRef(BenefitRef benefitRef) {
        this.benefitRef = benefitRef;
    }

    public BigDecimal getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(BigDecimal upperLimit) {
        this.upperLimit = upperLimit;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public String getFamilySize() {
        return familySize;
    }

    public void setFamilySize(String familySize) {
        this.familySize = familySize;
    }

//    public Corporate getCorporate() {
//        return corporate;
//    }
//
//    public void setCorporate(Corporate corporate) {
//        this.corporate = corporate;
//    }

    @Override
    public Long getId() {
        return idPremiumRate;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
         builder.add("idPremiumRate", idPremiumRate)
                 .add("premiumType", premiumType.toString())
                 .add("upperLimit", upperLimit)
                 .add("premium", premium)
                 .add("familySize", familySize);
        if(benefitRef !=null) {
            benefitRef.addJson(builder);
        }
//        if(corporate!=null){
//            corporate.addJson(builder);
//        }
    }
}
