package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;

/**
 * Created by akipkoech on 12/9/14.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"CORP_ID","LIMIT","FAM_SIZE","BENEFIT_CODE"})
})
@DiscriminatorValue("GROUP")
public class GroupRate extends PremiumRate {

    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "CORP_ID", nullable = false)
    private Corporate corporate;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "benefitCode",insertable=false, updatable=false,nullable = false)
//    private BenefitRef benefit;

    public GroupRate() {
        super();
    }

    public Corporate getCorporate() {
        return corporate;
    }

//    @Override
//    public BenefitRef getBenefit() {
//        return benefit;
//    }
//
//    @Override
//    public void setBenefit(BenefitRef benefit) {
//        this.benefit = benefit;
//    }

    public void setCorporate(Corporate corporate) {
        this.corporate = corporate;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        super.addJson(builder);

        //
        //Can't believe I was this silly!!
        //
        //        builder.add("idPremiumRate", idPremiumRate)
        //                .add("premiumType", premiumType.toString())
        //                .add("benefit", benefit)
        //                .add("upperLimit", upperLimit)
        //                .add("premium", premium)
        //                .add("familySize", familySize);
        corporate.addJson(builder);


    }

}
