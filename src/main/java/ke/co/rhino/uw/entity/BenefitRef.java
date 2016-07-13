package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by akipkoech on 25/05/15.
 */
@Entity@Table(name = "BENEFIT_REF")
public class BenefitRef extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long benefitCode;
    //@Column(unique = true)
    private String benefitName;
    private String description;
    @OneToMany(mappedBy = "benefitRef")
    private List<CorpBenefit> corpBenefits;
    @OneToMany(mappedBy = "benefitRef")
    private List<PremiumRate> premiumRates;
    @Convert(converter = LocalDateTimePersistenceConverter.class)
    private LocalDateTime lastUpdate;


    public BenefitRef() {
    }

    public BenefitRef(BenefitRefBuilder benefitRefBuilder) {
        this.benefitName = benefitRefBuilder.benefitName;
        this.description = benefitRefBuilder.description;
        this.benefitCode = benefitRefBuilder.benefitCode;
        this.lastUpdate = benefitRefBuilder.lastUpdate;
    }

    public static class BenefitRefBuilder {
        private final String benefitName;
        private String description;
        private LocalDateTime lastUpdate;
        private Long benefitCode;

        public BenefitRefBuilder(String benefitName) {
            this.benefitName = benefitName;
        }

        public BenefitRefBuilder description(String description){
            this.description = description;
            return this;
        }

        public BenefitRefBuilder lastUpdate(LocalDateTime lastUpdate){
            this.lastUpdate = lastUpdate;
            return this;
        }

        public BenefitRefBuilder benefitCode(Long benefitCode){
            this.benefitCode = benefitCode;
            return this;
        }

        public BenefitRef build(){
            return new BenefitRef(this);
        }
    }

    public Long getBenefitCode() {
        return benefitCode;
    }

    public String getBenefitName() {
        return benefitName;
    }

    public String getDescription() {
        return description;
    }

    public List<CorpBenefit> getCorpBenefits() {
        return corpBenefits;
    }

    public List<PremiumRate> getPremiumRates() {
        return premiumRates;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public Long getId() {
        return benefitCode;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("benefitCode",benefitCode)
                .add("benefitName", benefitName)
                .add("description", description)
                .add("lastUpdate", lastUpdate == null ? "" : DATE_FORMATTER_yyyyMMddHHmm.format(lastUpdate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BenefitRef)) return false;

        BenefitRef that = (BenefitRef) o;

        return benefitName.equalsIgnoreCase(that.benefitName);

    }

    @Override
    public int hashCode() {
        return benefitName.hashCode();
    }

    @Override
    public String toString() {
        return "BenefitRef{" +
                "benefitCode=" + benefitCode +
                ", benefitName='" + benefitName + '\'' +
                '}';
    }
}
