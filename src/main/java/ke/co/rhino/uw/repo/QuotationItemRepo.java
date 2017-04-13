package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.Quotation;
import ke.co.rhino.uw.entity.QuotationItem;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


/**
 * Created by user on 29/03/2017.
 */
public interface QuotationItemRepo extends PagingAndSortingRepository<QuotationItem,Long>,JpaSpecificationExecutor<QuotationItem> {

    long countByQuotation(Quotation quotation);
    List<QuotationItem> findByQuotation(Quotation quotation);
}
