package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Quotation;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by user on 29/03/2017.
 */
public interface IQuotationService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Quotation> create(LocalDate quotationDate, Long idCorporate, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Quotation> delete(Long idQuotation,String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<List<Quotation>> findByCorporate(Long idCorporate, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Quotation>> findByDate(LocalDate quotationDate, int page, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Quotation>> findByDateBetween(LocalDate date1, LocalDate date2, int page, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Quotation> findOne(Long idQuotation, String actionUsername);
}
