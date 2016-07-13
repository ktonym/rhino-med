package ke.co.rhino.care.repo;

import ke.co.rhino.care.entity.PreAuth;
import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 25/04/2016.
 */
public interface PreAuthRepo extends JpaRepository<PreAuth,Long> {

    /*@Query("SELECT p FROM PreAuth p WHERE p.corpMemberBenefit = :corpMemberBenefit")
    Stream<PreAuth> findByMemberBenefit(@Param("corpMemberBenefit") CorpMemberBenefit corpMemberBenefit);*/

    Stream<PreAuth> findByCorpMemberBenefit(CorpMemberBenefit corpMemberBenefit);

    Stream<PreAuth> findByProvider(ServiceProvider serviceProvider);

    Stream<PreAuth> findByPreAuthDate(LocalDate preAuthDate);

    Stream<PreAuth> findByPreAuthDateBetween(LocalDate start, LocalDate end);

    long countByCorpMemberBenefit(CorpMemberBenefit corpMemberBenefit);
}
