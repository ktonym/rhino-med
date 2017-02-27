package ke.co.rhino.claim.entity;

import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.List;

/**
 * Created by user on 05-Feb-17.
 */
@Entity
public class Procedure extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long procedureId;
    @Column(unique = true)
    private String cptCode;
    private String procedureDesc;
    @OneToMany(mappedBy = "procedure")
    private List<Treatment> treatments;

    public Procedure() {
    }

    public Procedure(ProcedureBuilder builder) {
        this.procedureId = builder.procedureId;
        this.procedureDesc = builder.procedureDesc;
        this.cptCode = builder.cptCode;
    }

    public static class ProcedureBuilder{
        private Long procedureId;
        private String cptCode;
        private String procedureDesc;

        public ProcedureBuilder() {
        }

        public ProcedureBuilder procedureId(Long procedureId){
            this.procedureId = procedureId;
            return this;
        }

        public ProcedureBuilder cptCode(String cptCode){
            this.cptCode = cptCode;
            return this;
        }

        public ProcedureBuilder procedureDesc(String procedureDesc){
            this.procedureDesc = procedureDesc;
            return this;
        }

        public Procedure build(){
            return new Procedure(this);
        }
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public String getCptCode() {
        return cptCode;
    }

    public String getProcedureDesc() {
        return procedureDesc;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    @Override
    public Long getId() {
        return procedureId;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("procedureId", procedureId)
                .add("cptCode", cptCode)
                .add("procedureDesc", procedureDesc);
    }
}
