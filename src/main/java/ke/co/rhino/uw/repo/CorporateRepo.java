package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by akipkoech on 27/01/2016.
 */
public interface CorporateRepo extends JpaRepository<Corporate, Long> {

    @Query("SELECT c FROM Corporate c WHERE UPPER(c.name) = UPPER(:name)")
    Corporate findByNameEqualsIgnoreCase(@Param("name") String name);

    @Query("SELECT c FROM Corporate c WHERE UPPER(c.abbreviation) = UPPER(:abbrev)")
    Corporate findByAbbrevEqualsIgnoreCase(@Param("abbrev") String abbrev);

    @Query("SELECT c FROM Corporate c WHERE UPPER(c.email) = UPPER(:email)")
    Corporate findByEmailEqualsIgnoreCase(@Param("email") String email);

    @Query("SELECT c FROM Corporate c WHERE UPPER(c.pin) = UPPER(:pin)")
    Corporate findByPinEqualsIgnoreCase(@Param("pin") String pin);

    List<Corporate> findByJoinedAfter(LocalDate localDate);

    List<Corporate> findByJoinedBefore(LocalDate localDate);

    List<Corporate> findByLastUpdateAfter(LocalDateTime localDate);

}


