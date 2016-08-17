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
    private CorpAnniv corpAnniv;
    private Integer commRate;
    private Integer whTaxRate;
    private Boolean coPay;
    @OneToMany(mappedBy = "regulation")
    private List<CoPay> coPayList;

    public Regulation() {
    }

    public Regulation(RegulationBuilder builder) {
        this.idRegulation = builder.idRegulation;
        this.corpAnniv = builder.anniv;
        this.commRate = builder.commRate;
        this.whTaxRate = builder.whTaxRate;
        this.coPay = builder.coPay;
        this.coPayList.addAll(builder.coPayList);
    }

    public static class RegulationBuilder{

        private Long idRegulation;
        private final CorpAnniv anniv;
        private Integer commRate;
        private Integer whTaxRate;
        private Boolean coPay;
        private List<CoPay> coPayList;

        public RegulationBuilder(CorpAnniv anniv) {
            this.anniv = anniv;
        }

        public RegulationBuilder idRegulation(Long idRegulation){
            this.idRegulation = idRegulation;
            return this;
        }

        public RegulationBuilder commRate(Integer commRate){
            this.commRate = commRate;
            return this;
        }

        public RegulationBuilder whTaxRate(Integer whTaxRate){
            this.whTaxRate = whTaxRate;
            return this;
        }

        public RegulationBuilder coPay(Boolean coPay){
            this.coPay = coPay;
            return this;
        }

        public RegulationBuilder coPayList(List<CoPay> coPayList){
            this.coPayList = coPayList;
            return this;
        }

        public Regulation build(){
            return new Regulation(this);
        }
    }

    public Long getIdRegulation() {
        return idRegulation;
    }

    public CorpAnniv getCorpAnniv() {
        return corpAnniv;
    }

    public Integer getCommRate() {
        return commRate;
    }

    public Integer getWhTaxRate() {
        return whTaxRate;
    }

    public Boolean getCoPay() {
        return coPay;
    }

    public List<CoPay> getCoPayList() {
        return coPayList;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idRegulation",idRegulation)
                .add("commRate", commRate)
                .add("whTaxRate",whTaxRate)
                .add("coPay",coPay);
        corpAnniv.addJson(builder);
    }

    @Override
    public Long getId() {
        return idRegulation;
    }
}
