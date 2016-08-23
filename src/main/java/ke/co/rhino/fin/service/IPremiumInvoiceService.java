package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.BusinessClass;
import ke.co.rhino.fin.entity.PremiumInvoice;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;

/**
 * Created by user on 23-Aug-16.
 */
public interface IPremiumInvoiceService {

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS','ROLE_UW_MANAGERS')")
    Result<PremiumInvoice> create(BusinessClass businessClass,
                                  LocalDate invoiceDate,
                                  Long idCorpBenefit,
                                  Long idParentPremium,
                                  String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS','ROLE_UW_MANAGERS')")
    Result<PremiumInvoice> update(Long idPremiumInvoice,
                                  String invoiceNumber,
                                  BusinessClass businessClass,
                                  LocalDate invoiceDate,
                                  Long idCorpBenefit,
                                  String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<PremiumInvoice>> findAll(Long idCorporate,int page, int size, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_SUPERVISORS','ROLE_UW_MANAGERS')")
    Result<PremiumInvoice> delete(Long idPremiumInvoice,String actionUsername);
}
