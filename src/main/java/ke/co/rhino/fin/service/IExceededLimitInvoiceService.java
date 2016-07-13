package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.ExceededLimitInvoice;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;

/**
 * Created by akipkoech on 08/07/2016.
 */
public interface IExceededLimitInvoiceService {

    @PreAuthorize("hasAnyRole('ROLE_CLM_MANAGERS','ROLE_CLM_ANALYST')")
    Result<ExceededLimitInvoice> create(Long idMember, Long idCorpAnniv, Long idCorpBenefit, BigDecimal amount );

    @PreAuthorize("hasAnyRole('ROLE_CLM_MANAGERS','ROLE_CLM_ANALYST')")
    Result<ExceededLimitInvoice> update(Long idXLInvoice, BigDecimal amount );

    @PreAuthorize("isAuthenticated()")
    Result<Page<ExceededLimitInvoice>> findAll();

    @PreAuthorize("hasAnyRole('ROLE_CLM_MANAGERS','ROLE_CLM_ANALYST')")
    Result<Page<ExceededLimitInvoice>> findByMemberAnniversary(Long idMember, Long idCorpAnniv);

    @PreAuthorize("hasAnyRole('ROLE_CLM_MANAGERS','ROLE_CLM_ANALYST')")
    Result<Page<ExceededLimitInvoice>> findByCorpAnniversary(Long idCorpAnniv);

}
