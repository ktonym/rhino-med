package ke.co.rhino.fin.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
    @ManyToOne
    private FundInvoice fundInvoice;
    private BigDecimal amount;
    private String notes;

    public AdminFee() {
    }

    public AdminFee(AdminFeeBuilder adminFeeBuilder) {
       // this.adminFeeType = adminFeeBuilder.adminFeeType;
        this.amount = adminFeeBuilder.amount;
        this.idAdminFee = adminFeeBuilder.idAdminFee;
        this.fundInvoice = adminFeeBuilder.fundInvoice;
        this.notes = adminFeeBuilder.notes;
    }

    public static class AdminFeeBuilder{

        //private final AdminFeeType adminFeeType;
        private FundInvoice fundInvoice;
        private BigDecimal amount;
        private Long idAdminFee;
        private String notes;

        public AdminFeeBuilder(FundInvoice fundInvoice) {
            this.fundInvoice = fundInvoice;
        }

//        public AdminFeeBuilder fundInvoice(){
//            this.fundInvoice = fundInvoice;
//            return this;
//        }

        public AdminFeeBuilder amount(BigDecimal amount){
            this.amount = amount;
            return this;
        }

        public AdminFeeBuilder idAdminFee(Long idAdminFee){
            this.idAdminFee=idAdminFee;
            return this;
        }

        public AdminFeeBuilder notes(String notes){
            this.notes = notes;
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
        builder.add("idAdminFee",idAdminFee)
                .add("notes",notes)
                //.add("adminFeeType",adminFeeType.toString())
                .add("amount", amount);
        fundInvoice.addJson(builder);
    }

    public Long getIdAdminFee() {
        return idAdminFee;
    }

    public FundInvoice getFundInvoice() {
        return fundInvoice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getNotes() {
        return notes;
    }
}
