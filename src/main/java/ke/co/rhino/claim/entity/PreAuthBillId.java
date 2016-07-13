package ke.co.rhino.claim.entity;

import ke.co.rhino.care.entity.PreAuth;

import java.io.Serializable;

/**
 * Created by ktonym on 1/10/15.
 */
public class PreAuthBillId implements Serializable {

    Bill bill;
    PreAuth preAuth;

    public PreAuthBillId() {
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public PreAuth getPreAuth() {
        return preAuth;
    }

    public void setPreAuth(PreAuth preAuth) {
        this.preAuth = preAuth;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bill == null) ? 0 : bill.hashCode());
		result = prime * result + ((preAuth == null) ? 0 : preAuth.hashCode());
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
		PreAuthBillId other = (PreAuthBillId) obj;
		if (bill == null) {
			if (other.bill != null)
				return false;
		} else if (!bill.equals(other.bill))
			return false;
		if (preAuth == null) {
			if (other.preAuth != null)
				return false;
		} else if (!preAuth.equals(other.preAuth))
			return false;
		return true;
	}
        
}
