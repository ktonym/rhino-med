package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by user on 10/02/2017.
 */
@Entity@IdClass(MemberCategoryId.class)
public class MemberCategory extends AbstractEntity implements EntityItem<MemberCategoryId> {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;
    private Boolean active;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate wef;

    public MemberCategory() {
    }

    public MemberCategory(MemberCategoryBuilder builder) {
        this.active = builder.active;
        this.wef = builder.wef;
        this.category = builder.category;
        this.member = builder.member;
    }

    public static class MemberCategoryBuilder{

        private final Category category;
        private final Member member;
        private LocalDate wef;
        private Boolean active;

        public MemberCategoryBuilder(Category category, Member member) {
            this.category = category;
            this.member = member;
        }

        public MemberCategoryBuilder wef(LocalDate wef){
            this.wef = wef;
            return this;
        }

        public MemberCategoryBuilder active(Boolean active){
            this.active = active;
            return  this;
        }

        public MemberCategory build(){
            return new MemberCategory(this);
        }

    }

    public Category getCategory() {
        return category;
    }

    public Member getMember() {
        return member;
    }

    public Boolean isActive() {
        return active;
    }

    public LocalDate getWef() {
        return wef;
    }

    //creating this just because I need to modify this record's active to false in a for loop

    public void setActive(Boolean active){
        this.active = active;
    }

    @Override
    public MemberCategoryId getId() {
        return new MemberCategoryId(category,member);
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("wef", wef == null ? "" : DATE_FORMATTER_yyyyMMdd.format(wef))
                .add("active", active ? "Y" : "N");
        member.addJson(builder);
        category.addJson(builder);
    }
}
