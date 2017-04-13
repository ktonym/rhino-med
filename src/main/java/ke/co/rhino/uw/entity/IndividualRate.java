package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;

/**
 * Created by akipkoech on 12/9/14.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"MIN_AGE","MAX_AGE","LIMIT","FAM_SIZE","BENEFIT_CODE"})
})
@DiscriminatorValue("INDIVIDUAL")
public class IndividualRate extends PremiumRate {

    @Column(name = "MIN_AGE")
    private Integer minAge;
    @Column(name = "MAX_AGE")
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
