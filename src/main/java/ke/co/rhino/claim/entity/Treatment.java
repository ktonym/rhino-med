package ke.co.rhino.claim.entity;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.entity.EntityItem;
import ke.co.rhino.uw.entity.LocalDatePersistenceConverter;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by user on 05-Feb-17.
 */
@Entity
public class Treatment extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long treatmentId;
    @ManyToOne
    private Procedure procedure;
    @ManyToOne
    private ServiceProvider serviceProvider;
    @ManyToOne
    /*@JoinColumns({
            @JoinColumn(name = "idMember",referencedColumnName = "idMember",nullable = false),
            @JoinColumn(name = "idCorpAnniv",referencedColumnName = "idCorpAnniv",nullable = false),
            @JoinColumn(name = "idCorpBenefit",referencedColumnName = "idCorpBenefit", nullable = false)
    })*/
    @JoinColumn(name = "idCorpMemberBenefit", referencedColumnName = "idCorpMemberBenefit", nullable = false)
    private CorpMemberBenefit corpMemberBenefit;
    @ManyToOne
    private Assessment assessment;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate treatmentDate;


    public Treatment() {
    }

    public Treatment(TreatmentBuilder builder) {
        this.treatmentId = builder.treatmentId;
        this.procedure = builder.procedure;
        this.serviceProvider = builder.serviceProvider;
        this.assessment = builder.assessment;
    }


    public static class TreatmentBuilder{
        private Long treatmentId;
        private Procedure procedure;
        private ServiceProvider serviceProvider;
        public Assessment assessment;
        private LocalDate treatmentDate;

        public TreatmentBuilder() {
        }

        public TreatmentBuilder treatmentId(Long treatmentId){
            this.treatmentId = treatmentId;
            return this;
        }

        public TreatmentBuilder procedure(Procedure procedure){
            this.procedure = procedure;
            return this;
        }

        public TreatmentBuilder serviceProvider(ServiceProvider provider){
            this.serviceProvider = provider;
            return this;
        }

        public TreatmentBuilder assessment(Assessment assessment){
            this.assessment = assessment;
            return this;
        }

        public TreatmentBuilder treatmentDate(LocalDate treatmentDate){
            this.treatmentDate = treatmentDate;
            return this;
        }

        public Treatment build(){
            return new Treatment(this);
        }

    }

    public Long getTreatmentId() {
        return treatmentId;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public CorpMemberBenefit getCorpMemberBenefit() {
        return corpMemberBenefit;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public LocalDate getTreatmentDate() {
        return treatmentDate;
    }

    @Override
    public Long getId() {
        return treatmentId;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("treatmentId", treatmentId)
                .add("treatmentDate", treatmentDate==null?"":DATE_FORMATTER_yyyyMMdd.format(treatmentDate));
        procedure.addJson(builder);
        serviceProvider.addJson(builder);
        assessment.addJson(builder);
    }
}
