package ke.co.rhino.care.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by akipkoech on 30/06/2016.
 */
@Entity
@Table(name="DIAGNOSIS")
public class Diagnosis extends AbstractEntity implements EntityItem<Long> {

    @Id
    private Long code;
    String description;
    @OneToMany
    private List<MemberDiagnosis> memberDiagnosisList;


    public Diagnosis() {
    }

    public Diagnosis(DiagnosisBuilder diagnosisBuilder) {
        this.code=diagnosisBuilder.code;
        this.description=diagnosisBuilder.description;
    }

    public static class DiagnosisBuilder{
        private Long code;
        private final String description;

        public DiagnosisBuilder(String description) {
            this.description = description;
        }

        public DiagnosisBuilder code(Long code){
            this.code=code;
            return this;
        }

        public Diagnosis build(){
            return new Diagnosis(this);
        }

    }

    public Long getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("code",code)
                .add("description",description);
    }

    @Override
    public Long getId() {
        return code;
    }
}
