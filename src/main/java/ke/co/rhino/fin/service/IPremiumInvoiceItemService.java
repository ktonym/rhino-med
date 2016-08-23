package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.PremiumInvoiceItem;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by akipkoech on 23/08/2016.
 */
public interface IPremiumInvoiceItemService {

    @PreAuthorize("hasAnyRole('ROLE_UW_SUPERVISORS','ROLE_UNDERWRITERS')")
    Result<Page<PremiumInvoiceItem>> create(Long idPremiumInvoice, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<PremiumInvoiceItem>> findAll(Long idPremiumInvoice, int page, int size, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UW_SUPERVISORS')")
    Result<String> delete(Long idPremiumInvoice, String actionUsername);

}
