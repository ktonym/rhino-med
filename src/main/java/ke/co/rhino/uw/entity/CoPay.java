package ke.co.rhino.uw.entity;

import ke.co.rhino.care.entity.ServiceProvider;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by akipkoech on 12/8/14.
 */
@Entity
public class CoPay extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCoPay;
    @OneToOne
    @JoinColumn(name = "provider_id") //to be redefined in a micro service edition
    private ServiceProvider serviceProvider;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="regulation_id") //to be redefined in a micro service edition
    private Regulation regulation;
    private BigDecimal coPayAmount;

    public CoPay() {
    }

    public CoPay(CoPayBuilder coPayBuilder) {
        this.serviceProvider = coPayBuilder.serviceProvider;
        this.regulation = coPayBuilder.regulation;
        this.coPayAmount = coPayBuilder.coPayAmount;
    }

    public static class CoPayBuilder{
        private ServiceProvider serviceProvider;
        private final Regulation regulation;
        private final BigDecimal coPayAmount;

        public CoPayBuilder(ServiceProvider serviceProvider, Regulation regulation, BigDecimal coPayAmount) {
            this.serviceProvider = serviceProvider;
            this.regulation = regulation;
            this.coPayAmount = coPayAmount;
        }

        public CoPay build(){
            return new CoPay(this);
        }
    }

    public Long getIdCoPay() {
        return idCoPay;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    public BigDecimal getCoPayAmount() {
        return coPayAmount;
    }

    @Override
    public Long getId() {
        return idCoPay;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idCoPay", idCoPay)
                .add("coPayAmount",coPayAmount);
        serviceProvider.addJson(builder);
        regulation.addJson(builder);
    }
}
