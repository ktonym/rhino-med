package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.Intermediary;
import ke.co.rhino.uw.entity.IntermediaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 4/7/15.
 */
public interface IntermediaryRepo extends JpaRepository<Intermediary,Long> {

    @Query("SELECT i FROM Intermediary i WHERE UPPER(i.name) = UPPER(:name)")
    Intermediary findByNameEqualsIgnoreCase(@Param("name") String name);

    @Query("SELECT i FROM Intermediary i WHERE UPPER(i.pin) = UPPER(:pin)")
    Intermediary findByPinEqualsIgnoreCase(@Param("pin") String pin);

    List<Intermediary> findByType(IntermediaryType intermediaryType);
    List<Intermediary> findByJoinDateAfter(LocalDate joinDate);
    List<Intermediary> findByJoinDateBefore(LocalDate joinDate);
    List<Intermediary> findByNameLike(String searchStr);

}
