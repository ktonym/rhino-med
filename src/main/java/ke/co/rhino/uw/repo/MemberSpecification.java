package ke.co.rhino.uw.repo;


import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberAnniversary;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

/**
 * Created by user on 25/03/2017.
 */
public class MemberSpecification implements Specification<Member>{

    private SearchCriteria criteria;

    public MemberSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")){
            return builder.greaterThanOrEqualTo(root.<String> get(criteria.getKey()),criteria.getValue().toString());
        } else if(criteria.getOperation().equalsIgnoreCase("<")){
            return builder.lessThan(root.<String> get(criteria.getKey()),criteria.getValue().toString());
        } else if(criteria.getOperation().equalsIgnoreCase(":")){
            if(root.get(criteria.getKey()).getJavaType() == String.class){
                return builder.like(root.<String> get(criteria.getKey()),"%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()),criteria.getValue());
            }
        } /*else if(criteria.getOperation().equalsIgnoreCase("in")){
            //SetJoin<Member,MemberAnniversary> memberMemberAnnivSetJoin = root.join(Member_.memberAnniversaries)
            builder.in(root.<String> get(criteria.getKey()),criteria.getValue().toString());
        }*/
        return null;
    }


    /**
    *  Sample usage
    *  public List<Member> findByCorporate(Long corpId){
    *  Corporate corp = corpRepo.findOne(idCorporate);
    *  if(corp!=null){
    *       MemberSpecification spec = new MemberSpecification(new SearchCriteria("corporate",":",corp));
    *       List<Member> members = repo.findAll(spec);
    *       return members;
    *  }
    *  return null;
    *  }
    */

    /**
     *  Sample multiple usage
     *   public List<Member> findByCorporateAndNameLike(Long corpId, String searchStr){
     *       Corporate corp = corpRepo.findOne(idCorporate);
     *       if(corp!=null){
     *             MemberSpecification spec1 = new MemberSpecification(new SearchCriteria("corporate",":", corp);
     *             MemberSpecification spec2 = new MemberSpecification(new SearchCriteria("surname",":",searchStr);
     *
     *             List<Member> results = repo.findAll(Specifications.where(spec1).and(spec2));
     *             return results;
     *       }
     *       return null;
     *
     *   }
     */

}
