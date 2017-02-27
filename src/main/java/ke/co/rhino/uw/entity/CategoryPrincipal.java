package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by ktonym on 1/2/15.
 */
@Entity @IdClass(CategoryPrincipalId.class)
public class CategoryPrincipal extends AbstractEntity implements EntityItem<CategoryPrincipalId>{

    private Boolean active;
    @Convert(converter = LocalDatePersistenceConverter.class)
    private LocalDate wef;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "principal_id")
    private Principal principal;


    public CategoryPrincipal() {
    }

    public CategoryPrincipal(CategoryPrincipalBuilder builder) {
        this.category = builder.category;
        this.principal = builder.principal;
        this.active = builder.active;
        this.wef = builder.wef;
    }

    public static class CategoryPrincipalBuilder{

        private final Category category;
        private final Principal principal;
        private LocalDate wef;
        private Boolean active;

        public CategoryPrincipalBuilder(Category category, Principal principal) {
            this.category = category;
            this.principal = principal;
        }

        public CategoryPrincipalBuilder wef(LocalDate wef){
            this.wef = wef;
            return this;
        }

        public CategoryPrincipalBuilder active(Boolean active){
            this.active = active;
            return  this;
        }

        public CategoryPrincipal build(){
            return new CategoryPrincipal(this);
        }

    }

    public Boolean isActive() {
        return active;
    }

    public LocalDate getWef() {
        return wef;
    }

    public Category getCategory() {
        return category;
    }

    public Principal getPrincipal() {
        return principal;
    }

    //creating this just because I need to modify this record's active to false in a for loop

    public void setActive(Boolean active){
        this.active = active;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        category.addJson(builder);
        principal.addJson(builder);
        builder.add("active", active ? "Y" : "N")
                .add("wef", wef == null ? "" : DATE_FORMATTER_yyyyMMdd.format(wef));

    }

    @Override
    public CategoryPrincipalId getId() {
        return new CategoryPrincipalId(category, principal);
    }
}
