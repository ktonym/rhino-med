package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.Quotation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by user on 28/03/2017.
 */
public class QuotationSpecification implements Specification<Quotation> {

    private SearchCriteria criteria;

    public QuotationSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Quotation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if(criteria.getOperation().equalsIgnoreCase(">")){
            return builder.greaterThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } else if(criteria.getOperation().equalsIgnoreCase(":")){
            if(root.get(criteria.getKey()).getJavaType() == String.class){
                return builder.like(root.<String> get(criteria.getKey()),"%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()),criteria.getValue());
            }
        } else if(criteria.getOperation().equalsIgnoreCase("<")){
            return builder.lessThan(root.<String> get(criteria.getKey()),criteria.getValue().toString());
        } else if(criteria.getOperation().equalsIgnoreCase("<=")){
            return builder.lessThanOrEqualTo(root.<String> get(criteria.getKey()), criteria.getValue().toString());
        } else if(criteria.getOperation().equalsIgnoreCase("btn")){
            return builder.between(root.<String> get(criteria.getKey()), criteria.getValue().toString(),criteria.getOptVal().toString());
        }
        return null;
    }
}
