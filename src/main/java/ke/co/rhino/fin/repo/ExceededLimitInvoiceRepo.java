package ke.co.rhino.fin.repo;

import ke.co.rhino.fin.entity.ExceededLimitInvoice;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by akipkoech on 08/07/2016.
 */
public interface ExceededLimitInvoiceRepo extends JpaRepository<ExceededLimitInvoice,Long> {

    Page<ExceededLimitInvoice> findByCorpMemberBenefit(CorpMemberBenefit corpMemberBenefit,Pageable pageable);

}
