package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by akipkoech on 12/8/14.
 */
@Entity
public class CoPay extends AbstractEntity implements EntityItem<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCoPay;
    @Column(name = "provider_id")
    private Integer idProvider;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="regulation_id")
    private Regulation regulation;
    private BigDecimal coPayAmount;

    public CoPay() {
    }

    public CoPay(CoPayBuilder coPayBuilder) {
        this.idProvider = coPayBuilder.idProvider;
        this.regulation = coPayBuilder.regulation;
        this.coPayAmount = coPayBuilder.coPayAmount;
    }

    public static class CoPayBuilder{
        private final Integer idProvider;
        private final Regulation regulation;
        private final BigDecimal coPayAmount;

        public CoPayBuilder(Integer idProvider, Regulation regulation, BigDecimal coPayAmount) {
            this.idProvider = idProvider;
            this.regulation = regulation;
            this.coPayAmount = coPayAmount;
        }

        public CoPay build(){
            return new CoPay(this);
        }
    }

    public Integer getIdCoPay() {
        return idCoPay;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    public BigDecimal getCoPayAmount() {
        return coPayAmount;
    }

    @Override
    public Integer getId() {
        return idCoPay;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idCoPay", idCoPay);
        builder.add("idProvider", idProvider);
        regulation.addJson(builder);
        builder.add("coPayAmount",coPayAmount);

    }
}
