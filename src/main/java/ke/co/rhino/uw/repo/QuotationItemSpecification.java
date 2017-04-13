package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.QuotationItem;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by user on 03/04/2017.
 */
public class QuotationItemSpecification implements Specification<QuotationItem> {

    private SearchCriteria criteria;

    public QuotationItemSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<QuotationItem> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if(criteria.getOperation().equalsIgnoreCase(">")){
            return builder.greaterThan(root.<String>get(criteria.getKey()),criteria.getValue().toString());
        } else if(criteria.getOperation().equalsIgnoreCase("<")){
            return builder.lessThan(root.<String>get(criteria.getKey()),criteria.getValue().toString());
        } else if(criteria.getOperation().equalsIgnoreCase(":")){
            if(root.get(criteria.getKey()).getJavaType()==String.class){
                return builder.like(root.<String>get(criteria.getKey()),"%"+criteria.getValue()+"%");
            } else {
                return builder.equal(root.<String>get(criteria.getKey()),criteria.getValue());
            }
        } else if(criteria.getOperation().equalsIgnoreCase("btn")){
            return builder.between(root.<String>get(criteria.getKey()),criteria.getValue().toString(),criteria.getOptVal().toString());
        }

        return null;
    }
}
