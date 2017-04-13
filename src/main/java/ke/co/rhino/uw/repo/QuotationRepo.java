package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by user on 28/03/2017.
 */

public interface QuotationRepo extends PagingAndSortingRepository<Quotation,Long>, JpaSpecificationExecutor<Quotation> {

    Page<Quotation> findAll(Pageable pageable);
    /*Page<Quotation> findByQuotationDate(LocalDate date,Pageable pageable);
    Page<Quotation> findByQuotationDateBetween(LocalDate date1, LocalDate date2, Pageable pageable);*/
    List<Quotation> findByCorporate(Corporate corp);
}
