package ke.co.rhino.ri.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by akipkoech on 12/9/14.
 */
@Entity
@DiscriminatorValue("QUOTA_SHARE")
public class QuotaShare extends ReinsuranceTreaty {

    private Integer cessionRate;
    private Integer retentionRate;
    private Integer taxRate;
    private Integer commRate;

    public QuotaShare() {
    }

    public Integer getCessionRate() {
        return cessionRate;
    }

    public void setCessionRate(Integer cessionRate) {
        this.cessionRate = cessionRate;
    }

    public Integer getRetentionRate() {
        return retentionRate;
    }

    public void setRetentionRate(Integer retentionRate) {
        this.retentionRate = retentionRate;
    }

    public Integer getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Integer taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getCommRate() {
        return commRate;
    }

    public void setCommRate(Integer commRate) {
        this.commRate = commRate;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        super.addJson(builder);
        builder.add("cessionRate",cessionRate)
                .add("retentionRate",retentionRate)
                .add("taxRate",taxRate)
                .add("commRate",commRate);

    }
}
