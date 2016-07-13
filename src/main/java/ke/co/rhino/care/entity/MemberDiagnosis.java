package ke.co.rhino.care.entity;

import ke.co.rhino.claim.entity.Bill;
import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by akipkoech on 30/06/2016.
 */
@Entity
@Table(name="MEM_DIAGNOSIS")
public class MemberDiagnosis extends AbstractEntity implements EntityItem<MemberDiagnosisId> {

    @Id
    @ManyToOne(optional=false)
    private Diagnosis diagnosis;
    @Id
    @ManyToOne(optional = false)
    private Bill bill;

    public MemberDiagnosis() {
    }

    public MemberDiagnosis(MemberDiagnosisBuilder memberDiagnosisBuilder) {
        this.bill=memberDiagnosisBuilder.bill;
        this.diagnosis=memberDiagnosisBuilder.diagnosis;
    }

    public static class MemberDiagnosisBuilder{
        private Diagnosis diagnosis;
        private final Bill bill;

        public MemberDiagnosisBuilder(Bill bill){
            this.bill = bill;
        }
        public MemberDiagnosisBuilder diagnosis(Diagnosis diagnosis) {
            this.diagnosis = diagnosis;
            return this;
        }
        public MemberDiagnosis build(){
            return new MemberDiagnosis(this);
        }

    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public Bill getBill() {
        return bill;
    }

    @Override
    public MemberDiagnosisId getId() {
        return new MemberDiagnosisId(bill,diagnosis);
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        bill.addJson(builder);
        diagnosis.addJson(builder);
    }
}
