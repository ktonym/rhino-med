package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 27/01/2016.
 */
public interface CorpAnnivRepo extends JpaRepository<CorpAnniv, Long> {

    List<CorpAnniv> findByCorporate(Corporate corporate);

    CorpAnniv findByCorporateAndAnniv(Corporate corporate, Integer anniv);

    @Query("SELECT max(a.anniv) FROM CorpAnniv a WHERE a.corporate=:corporate")
    Integer getMax(@Param("corporate") Corporate corporate);

    //Stream<CorpAnniv> findByCorporate(Corporate corporate);

    long countByCorporate(Corporate corporate);
}
