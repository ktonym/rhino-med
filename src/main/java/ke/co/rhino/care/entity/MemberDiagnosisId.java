package ke.co.rhino.care.entity;

import ke.co.rhino.claim.entity.Bill;

import java.io.Serializable;

/**
 * Created by akipkoech on 30/06/2016.
 */
public class MemberDiagnosisId implements Serializable{

    private Bill bill;
    private Diagnosis diagnosis;

    public MemberDiagnosisId() {
    }

    public MemberDiagnosisId(Bill bill, Diagnosis diagnosis) {
        this.bill = bill;
        this.diagnosis = diagnosis;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Diagnosis getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberDiagnosisId)) return false;

        MemberDiagnosisId that = (MemberDiagnosisId) o;

        if (!bill.equals(that.bill)) return false;
        return diagnosis.equals(that.diagnosis);

    }

    @Override
    public int hashCode() {
        int result = bill.hashCode();
        result = 31 * result + diagnosis.hashCode();
        return result;
    }
}
