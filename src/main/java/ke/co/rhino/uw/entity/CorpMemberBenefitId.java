package ke.co.rhino.uw.entity;

import java.io.Serializable;

/**
 * Created by ktonym on 1/9/15.
 */
public class CorpMemberBenefitId implements Serializable{

    CorpBenefit benefit;
    MemberAnniversary memberAnniv;

    public CorpMemberBenefitId() {
    }

	public CorpMemberBenefitId(CorpBenefit benefit, MemberAnniversary memberAnniv) {
		this.benefit = benefit;
		this.memberAnniv = memberAnniv;
	}

	public CorpBenefit getBenefit() {
		return benefit;
	}

	public void setBenefit(CorpBenefit benefit) {
		this.benefit = benefit;
	}

	public MemberAnniversary getMemberAnniv() {
		return memberAnniv;
	}

	public void setMemberAnniv(MemberAnniversary memberAnniv) {
		this.memberAnniv = memberAnniv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((benefit == null) ? 0 : benefit.hashCode());
		result = prime * result + ((memberAnniv == null) ? 0 : memberAnniv.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorpMemberBenefitId other = (CorpMemberBenefitId) obj;
		if (benefit == null) {
			if (other.benefit != null)
				return false;
		} else if (!benefit.equals(other.benefit))
			return false;
		if (memberAnniv == null) {
			if (other.memberAnniv != null)
				return false;
		} else if (!memberAnniv.equals(other.memberAnniv))
			return false;
		return true;
	}



}
