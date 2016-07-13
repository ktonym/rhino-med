package ke.co.rhino.uw.entity;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ANNIV_ID","CAT"})})
public class Category extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idCategory;
    @Column(nullable = false, name = "CAT")
    private char cat;
	private String description;
	@OneToMany(mappedBy = "category")
	private List<CorpBenefit> corpBenefits;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ANNIV_ID",nullable = false)
    private CorpAnniv corpAnniv;
    @OneToMany(mappedBy = "category")
    private List<CategoryPrincipal> categoryPrincipal;

    public Category() {
    }

    public Category(CategoryBuilder categoryBuilder) {
        this.cat = categoryBuilder.cat;
        this.description = categoryBuilder.description;
        this.corpAnniv = categoryBuilder.corpAnniv;
        this.idCategory = categoryBuilder.idCategory;
    }

    public static class CategoryBuilder{

        private final char cat;
        private String description;
        private final CorpAnniv corpAnniv;
        private Long idCategory;

        public CategoryBuilder(char cat, CorpAnniv corpAnniv) {
            this.cat = cat;
            this.corpAnniv = corpAnniv;
        }

        public CategoryBuilder idCategory(Long idCategory){
            this.idCategory = idCategory;
            return this;
        }

        public CategoryBuilder description(String description){
            this.description = description;
            return this;
        }

        public Category build(){
            return new Category(this);
        }

    }

    public Long getIdCategory() {
        return idCategory;
    }

    public List<CategoryPrincipal> getCategoryPrincipal() {
        return categoryPrincipal;
    }

    public char getCat() {
        return cat;
    }

    public String getDescription() {
        return description;
    }

    public List<CorpBenefit> getCorpBenefits() {
        return corpBenefits;
    }

    public CorpAnniv getCorpAnniv() {
        return corpAnniv;
    }

    @Override
    public Long getId() {
        return idCategory;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idCategory", idCategory)
                .add("cat", cat)
                .add("description", description);
        if(corpAnniv !=null) {
            corpAnniv.addJson(builder);
        }
    }
}
