package ke.co.rhino.uw.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

/**
 * Created by user on 06/04/2017.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LIMIT","FAM_SIZE","BENEFIT_CODE"})
})
@DiscriminatorValue("STANDARD")
public class StdPremiumRate extends PremiumRate {

    public StdPremiumRate() {
    }

    public StdPremiumRate(StdPremiumRateBuilder builder) {
        this.benefitRef = builder.benefitRef;
        this.familySize = builder.familySize;
        this.upperLimit = builder.upperLimit;
        this.premium = builder.premium;
        this.idPremiumRate = builder.idPremiumRate;
    }

    public static class StdPremiumRateBuilder{
        private Long idPremiumRate;
        //private PremiumType premiumType;
        private BigDecimal upperLimit;
        private BigDecimal premium;
        private String familySize;
        private BenefitRef benefitRef;

        public StdPremiumRateBuilder() {
        }

        public StdPremiumRateBuilder idPremiumRate(Long idPremiumRate){
            this.idPremiumRate = idPremiumRate;
            return this;
        }

        public StdPremiumRateBuilder upperLimit(BigDecimal upperLimit){
            this.upperLimit = upperLimit;
            return this;
        }

        public StdPremiumRateBuilder premium(BigDecimal premium){
            this.premium = premium;
            return this;
        }

        public StdPremiumRateBuilder familySize(String familySize){
            this.familySize = familySize;
            return this;
        }

        public StdPremiumRateBuilder benefitRef(BenefitRef benefitRef){
            this.benefitRef = benefitRef;
            return this;
        }

        public StdPremiumRate build(){
            return new StdPremiumRate(this);
        }
    }
}
