package ke.co.rhino.claim.entity;

import ke.co.rhino.uw.entity.EntityItem;
import ke.co.rhino.uw.entity.LocalDatePersistenceConverter;
import ke.co.rhino.uw.entity.Member;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


/**
 * Created by user on 05-Feb-17.
 */
@Entity
public class Assessment extends AbstractEntity implements EntityItem<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long assessmentId;
    @Convert(converter=LocalDatePersistenceConverter.class)
    @Column(nullable = false)
    private LocalDate firstConsultationDate;
    @ManyToOne
    private Condition condition;
    private Boolean deceased;
    @ManyToOne
    private Member member;
    @OneToMany(mappedBy = "assessment")
    private List<Treatment> treatments;
    //private AssessmentStatus assessmentStatus; // TODO find out if necessary


    public Assessment() {
    }

    public Assessment(AssessmentBuilder builder) {
        this.assessmentId = builder.assessmentId;
        this.firstConsultationDate = builder.firstConsultationDate;
        this.condition = builder.condition;
        this.deceased = builder.deceased;
        this.member = builder.member;
    }

    public static class AssessmentBuilder{
        private Long assessmentId;
        private LocalDate firstConsultationDate;
        private Condition condition;
        private Boolean deceased;
        private Member member;

        public AssessmentBuilder() {
        }

        public AssessmentBuilder assessmentId(Long assessmentId){
            this.assessmentId = assessmentId;
            return this;
        }

        public AssessmentBuilder firstConsultation(LocalDate firstConsultationDate){
            this.firstConsultationDate = firstConsultationDate;
            return this;
        }

        public AssessmentBuilder condition(Condition condition){
            this.condition = condition;
            return this;
        }

        public AssessmentBuilder isDeceased(Boolean deceased){
            this.deceased = deceased;
            return this;
        }

        public AssessmentBuilder member(Member member){
            this.member = member;
            return this;
        }

        public Assessment build(){
            return new Assessment(this);
        }

    }

    public Long getAssessmentId() {
        return assessmentId;
    }

    public LocalDate getFirstConsultationDate() {
        return firstConsultationDate;
    }

    public Condition getCondition() {
        return condition;
    }

    public Boolean getDeceased() {
        return deceased;
    }

    public Boolean isDeceased(){
        return deceased;
    }

    public Member getMember() {
        return member;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    @Override
    public Long getId() {
        return assessmentId;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("assessmentId", assessmentId)
                .add("firstConsultationDate", firstConsultationDate==null?"":DATE_FORMATTER_yyyyMMdd.format(firstConsultationDate))
                .add("deceased",deceased?"Y":"N");
        condition.addJson(builder);
        member.addJson(builder);
    }
}
