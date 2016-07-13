package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by akipkoech on 12/9/14.
 */
@Entity
@DiscriminatorValue("INDIVIDUAL")
public class IndividualRate extends PremiumRate {

    private Integer minAge;
    private Integer maxAge;

    public IndividualRate() {
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        super.addJson(builder);
        builder.add("minAge", minAge)
                .add("maxAge", maxAge);

    }
}
