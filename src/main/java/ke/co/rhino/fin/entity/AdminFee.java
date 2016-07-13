package ke.co.rhino.fin.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by akipkoech on 13/06/2016.
 */
@Entity
public class AdminFee extends AbstractEntity implements EntityItem<Long>{

    @Id
    private Long idAdminFee;
    private AdminFeeType adminFeeType;
    @OneToMany(mappedBy = "adminFee")
    private List<FundInvoice> fundInvoiceList;
    private BigDecimal amount;

    public AdminFee() {
    }

    public AdminFee(AdminFeeBuilder adminFeeBuilder) {
        this.adminFeeType = adminFeeBuilder.adminFeeType;
        this.amount = adminFeeBuilder.amount;
        this.idAdminFee = adminFeeBuilder.idAdminFee;
    }

    public static class AdminFeeBuilder{

        private final AdminFeeType adminFeeType;
        private BigDecimal amount;
        private Long idAdminFee;

        public AdminFeeBuilder(AdminFeeType adminFeeType) {
            this.adminFeeType = adminFeeType;
        }

        public AdminFeeBuilder amount(BigDecimal amount){
            this.amount = amount;
            return this;
        }

        public AdminFeeBuilder idAdminFee(Long idAdminFee){
            this.idAdminFee=idAdminFee;
            return this;
        }

        public AdminFee build(){
            return new AdminFee(this);
        }

    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

    }

    public Long getIdAdminFee() {
        return idAdminFee;
    }

    public AdminFeeType getAdminFeeType() {
        return adminFeeType;
    }

    public List<FundInvoice> getFundInvoiceList() {
        return fundInvoiceList;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
