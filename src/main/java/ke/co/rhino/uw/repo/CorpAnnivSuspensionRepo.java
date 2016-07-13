package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.CorpAnnivSuspension;
import ke.co.rhino.uw.entity.Corporate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by akipkoech on 31/10/15.
 */
public interface CorpAnnivSuspensionRepo extends JpaRepository<CorpAnnivSuspension, Integer> {

    List<CorpAnnivSuspension> findByCorpAnniv(CorpAnniv corpAnniv);
    List<CorpAnnivSuspension> findByCorpAnniv_Corporate(Corporate corporate);

}
