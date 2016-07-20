package ke.co.rhino.ri.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;

/**
 * Created by akipkoech on 12/9/14.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="RI_TYPE",discriminatorType = DiscriminatorType.STRING)
public class ReinsuranceTreaty extends AbstractEntity implements EntityItem<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idRITreaty;

    @Column(name = "RI_TYPE",insertable = false,updatable = false)
    private ReinsuranceType rIType;

    private Integer rIPeriod;

    public ReinsuranceTreaty() {
    }

    public Integer getIdRITreaty() {
        return idRITreaty;
    }

    public void setIdRITreaty(Integer idRITreaty) {
        this.idRITreaty = idRITreaty;
    }

    public ReinsuranceType getRiType() {
        return rIType;
    }

    public void setRIType(ReinsuranceType riType) {
        this.rIType = riType;
    }

    public Integer getrIPeriod() {
        return rIPeriod;
    }

    public void setrIPeriod(Integer rIPeriod) {
        this.rIPeriod = rIPeriod;
    }

    @Override
    public Integer getId() {
        return idRITreaty;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idRITreaty",idRITreaty)
                .add("rIType", rIType.toString())
                .add("rIPeriod",rIPeriod);

    }
}
