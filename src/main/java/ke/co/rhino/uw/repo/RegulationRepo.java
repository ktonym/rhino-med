package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.Regulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Created by user on 15-Aug-16.
 */
public interface RegulationRepo extends PagingAndSortingRepository<Regulation,Long> {

    Optional<Regulation> findByCorpAnniv(CorpAnniv anniv);

    Page<Regulation> findByCorpAnniv_Corporate(Corporate corporate, Pageable pageable);

    Optional<Regulation> getOne(Long idRegulation);

}
