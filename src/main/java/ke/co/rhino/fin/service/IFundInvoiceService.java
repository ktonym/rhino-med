package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.AdminFee;
import ke.co.rhino.fin.entity.FundInvoice;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by akipkoech on 18/07/2016.
 */
public interface IFundInvoiceService {

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<FundInvoice> create(BigDecimal amount,LocalDate invoiceDate,
                               Long idCorpBenefit,String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<FundInvoice> update(Long idFundInvoice, String invoiceNumber,BigDecimal amount,LocalDate invoiceDate,
                               Long idCorpBenefit,String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<FundInvoice> remove(Long idFundInvoice,String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<FundInvoice>> findAll(int PageNum, int size, Long idCorporate,String actionUsername);

}
