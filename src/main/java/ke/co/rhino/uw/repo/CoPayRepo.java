package ke.co.rhino.uw.repo;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.uw.entity.CoPay;
import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.Regulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

/**
 * Created by akipkoech on 12/8/14.
 */
public interface CoPayRepo extends PagingAndSortingRepository<CoPay,Long> {

    Optional<CoPay> getOne(Long idCoPay);

    Page<CoPay> findByRegulation(Regulation regulation, Pageable pageable);

    long countByRegulation(Regulation regulation);

    Page<CoPay> findByRegulationCorpAnniv(CorpAnniv anniv,Pageable pageable);

    Page<CoPay> findByRegulationCorpAnnivCorporate(Corporate corporate, Pageable pageable);

    Optional<CoPay> findByServiceProviderAndRegulation(ServiceProvider sp,Regulation regulation);
}
