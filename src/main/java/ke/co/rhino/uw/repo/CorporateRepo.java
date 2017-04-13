package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.PlanType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Created by akipkoech on 27/01/2016.
 */
public interface CorporateRepo extends PagingAndSortingRepository<Corporate, Long> {

    @Query("SELECT c FROM Corporate c WHERE UPPER(c.name) = UPPER(:name)")
    Corporate findByNameEqualsIgnoreCase(@Param("name") String name);

    @Query("SELECT c FROM Corporate c WHERE UPPER(c.abbreviation) = UPPER(:abbrev)")
    Corporate findByAbbrevEqualsIgnoreCase(@Param("abbrev") String abbrev);

    @Query("SELECT c FROM Corporate c WHERE UPPER(c.email) = UPPER(:email)")
    Corporate findByEmailEqualsIgnoreCase(@Param("email") String email);

    @Query("SELECT c FROM Corporate c WHERE UPPER(c.pin) = UPPER(:pin)")
    Corporate findByPinEqualsIgnoreCase(@Param("pin") String pin);

    Page<Corporate> findByJoinedAfter(LocalDate localDate, Pageable pageable);

    Page<Corporate> findByJoinedBefore(LocalDate localDate, Pageable pageable);

    Page<Corporate> findByLastUpdateAfter(LocalDateTime localDate, Pageable pageable);

    Optional<Corporate> getOne(Long idCorporate);

    Page<Corporate> findByPlanType(PlanType planType, Pageable pageable);

    @Query("SELECT c FROM Corporate c WHERE c.annivs " +
            "IN (SELECT ca FROM CorpAnniv ca WHERE ca = :corpAnniv) ")
    Corporate findByCorpAnniv(@Param("corpAnniv")CorpAnniv corpAnniv);

}


