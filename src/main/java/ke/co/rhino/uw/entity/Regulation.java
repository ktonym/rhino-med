package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.List;

/**
 * Created by akipkoech on 12/8/14.
 */
@Entity
public class Regulation extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idRegulation;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "anniv_id",unique = true)
    private CorpAnniv anniv;
    private Integer commRate;
    private Integer whTaxRate;
    private Boolean coPay;
    @OneToMany(mappedBy = "regulation")
    private List<CoPay> coPayList;

    public Regulation() {
    }

    public Long getIdRegulation() {
        return idRegulation;
    }

    public void setIdRegulation(Long idRegulation) {
        this.idRegulation = idRegulation;
    }

    public CorpAnniv getAnniv() {
        return anniv;
    }

    public void setAnniv(CorpAnniv anniv) {
        this.anniv = anniv;
    }

    public Integer getCommRate() {
        return commRate;
    }

    public void setCommRate(Integer commRate) {
        this.commRate = commRate;
    }

    public Integer getWhTaxRate() {
        return whTaxRate;
    }

    public void setWhTaxRate(Integer whTaxRate) {
        this.whTaxRate = whTaxRate;
    }

    public Boolean getCoPay() {
        return coPay;
    }

    public void setCoPay(Boolean coPay) {
        this.coPay = coPay;
    }

    public List<CoPay> getCoPayList() {
        return coPayList;
    }

    public void setCoPayList(List<CoPay> coPayList) {
        this.coPayList = coPayList;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idRegulation",idRegulation)
                .add("commRate", commRate)
                .add("whTaxRate",whTaxRate)
                .add("coPay",coPay);
        anniv.addJson(builder);
    }

    @Override
    public Long getId() {
        return idRegulation;
    }
}
