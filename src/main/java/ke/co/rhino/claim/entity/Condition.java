package ke.co.rhino.claim.entity;

import ke.co.rhino.uw.entity.EntityItem;
import org.hibernate.annotations.Cache;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.List;

/**
 * Created by user on 05-Feb-17.
 */
@Entity
public class Condition extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long conditionId;
    @Column(unique = true)
    private String icdCode;
    private String description;
    @OneToMany(mappedBy = "condition")
    private List<Assessment> assessments;

    public Condition() {
    }

    public Condition(ConditionBuilder builder) {
        this.conditionId = builder.conditionId;
        this.icdCode = builder.icdCode;
        this.description = builder.description;
    }

    public static class ConditionBuilder{
        private Long conditionId;
        private String icdCode;
        private String description;

        public ConditionBuilder() {
        }

        public ConditionBuilder conditionId(Long conditionId){
            this.conditionId = conditionId;
            return this;
        }

        public ConditionBuilder icdCode(String icdCode){
            this.icdCode = icdCode;
            return this;
        }

        public ConditionBuilder description(String description){
            this.description = description;
            return this;
        }

        public Condition build(){
            return new Condition(this);
        }

    }

    public Long getConditionId() {
        return conditionId;
    }

    public String getIcdCode() {
        return icdCode;
    }

    public String getDescription() {
        return description;
    }

    public List<Assessment> getAssessments() {
        return assessments;
    }

    @Override
    public Long getId() {
        return conditionId;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("conditionId",conditionId)
                .add("icdCode", icdCode)
                .add("conditionDesc", description);
    }
}
