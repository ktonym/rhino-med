package ke.co.rhino.uw.entity;

import java.io.Serializable;

/**
 * Created by akipkoech on 09/11/2015.
 */
public class MemberAnnivId implements Serializable{

    Member member;
    CorpAnniv corpAnniv;

    public MemberAnnivId(Member member,CorpAnniv anniv) {
        this.member = member;
        this.corpAnniv = anniv;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public CorpAnniv getCorpAnniv() {
        return corpAnniv;
    }

    public void setCorpAnniv(CorpAnniv corpAnniv) {
        this.corpAnniv = corpAnniv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberAnnivId)) return false;

        MemberAnnivId that = (MemberAnnivId) o;

        if (!member.equals(that.member)) return false;
        return corpAnniv.equals(that.corpAnniv);

    }

    @Override
    public int hashCode() {
        int result = member.hashCode();
        result = 31 * result + corpAnniv.hashCode();
        return result;
    }
}
