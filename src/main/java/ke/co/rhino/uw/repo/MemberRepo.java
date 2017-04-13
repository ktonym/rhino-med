package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 12/8/14.
 */
public interface MemberRepo extends PagingAndSortingRepository<Member,Long>, JpaSpecificationExecutor<Member> {

    // List<Member>  findByPrincipal_CategoryPrincipal_Category_Anniv_Corporate(Corporate corporate);

    //Find paged results of the members by category
    Page<Member> findByMemberAnniversaries_CorpAnniv(CorpAnniv corpAnniv, Pageable pageable);
    Stream<Member> findByMemberCategories_Category_CorpAnniv(CorpAnniv anniv);
    Stream<Member>  findByMemberCategories_Category(Category category);

    //Find paged results of members by category
    Page<Member> findByMemberCategories_Category(Category category,Pageable pageable);
    Stream<Member>  findByCorporate(Corporate corporate);
    //TODO during implementation, remember to use try with resources

    /**
     * try (Stream<User> stream = repository.findAllByCustomQueryAndStream()) {
     * stream.forEach(�);
     * }
     * @param principal
     * @return
     */
    // To find dependants
    List<Member> findByPrincipal(Member principal);
    long countByPrincipal(Member principal);
    long countByCorporate(Corporate corporate);
    long countByMemberCategories_Category(Category category);
    Member findByMemberNo(String memberNo);

    //To find all members in a given corporate anniversary..
    @Query("SELECT m FROM Member m WHERE m.memberAnniversaries " +
            "IN ( SELECT ma FROM MemberAnniversary ma " +
            " WHERE ma.corpAnniv = :corpAnniv)")
    Page<Member> findByAnniversary(@Param("corpAnniv") CorpAnniv corpAnniv, Pageable pageable);

    //To find all members currently covered. Hospital list??
    @Query("SELECT m FROM Member m WHERE m.memberAnniversaries " +
            "IN (SELECT ma FROM MemberAnniversary ma " +
            " WHERE current_date BETWEEN ma.inception AND ma.expiry)")
    Page<Member> findAllCovered(Pageable pageable);

    //To find all suspended members.
    @Query("SELECT m FROM Member m WHERE m.memberAnniversaries " +
            "IN (SELECT ma FROM MemberAnniversary ma " +
            "WHERE current_date BETWEEN ma.inception AND ma.expiry " +
            "AND ma.memberSuspensions " +
            "IN (SELECT ms FROM MemberSuspension ms WHERE ms.reinstatementDate IS NULL ))")
    Page<Member> findSuspended(Pageable pageable);

    //All Active and reinstated members..
    @Query("SELECT m FROM Member m WHERE m.memberAnniversaries " +
            "            IN (SELECT ma FROM MemberAnniversary ma WHERE current_date BETWEEN ma.inception AND ma.expiry " +
            "                AND ( " +
            "                       ma.memberSuspensions IN (SELECT ms FROM MemberSuspension ms WHERE ms.reinstatementDate <= current_date ) " +
            "                       OR " +
            "                      COUNT(ma.memberSuspensions) = 0) )")
    Page<Member> findActive(Pageable pageable);

    Optional<Member> getOne(Long memberId);

    @Query("SELECT m FROM Member m WHERE m.principal IS NULL AND m.corporate = :corp")
    List<Member> findPrincipals(@Param("corp") Corporate corporate);


    //All Members not in a given anniversary
    //TODO include m.memberStatus is ACTIVE
    @Query("SELECT m FROM Member m WHERE  m.corporate = :corporate AND m.memberAnniversaries " +
            "           NOT IN (SELECT ma FROM MemberAnniversary ma WHERE ma.corpAnniv = :corpanniv)")
    //@Query("SELECT m FROM Member m WHERE  m.corporate = :corporate")
    Page<Member> findUncoveredInPolicy(@Param("corpanniv") CorpAnniv corpAnniv,
                                       @Param("corporate") Corporate corporate, Pageable pageable);
}