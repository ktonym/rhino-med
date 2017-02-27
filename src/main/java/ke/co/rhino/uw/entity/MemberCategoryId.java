package ke.co.rhino.uw.entity;

import java.io.Serializable;

/**
 * Created by user on 10/02/2017.
 */
public class MemberCategoryId implements Serializable{

    private Category category;
    private Member member;

    public MemberCategoryId(Category category, Member member) {
        this.category = category;
        this.member = member;
    }

    public Category getCategory() {
        return category;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberCategoryId that = (MemberCategoryId) o;

        if (!category.equals(that.category)) return false;
        return member.equals(that.member);

    }

    @Override
    public int hashCode() {
        int result = category.hashCode();
        result = 31 * result + member.hashCode();
        return result;
    }
}
