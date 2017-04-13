package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.QuotationItem;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by user on 03/04/2017.
 */
public interface IQuotationItemService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<QuotationItem> create(Long idQuotation, Long idPremiumRate, LocalDate quoteItemDate,Long discountLoadFactor,
                                 /*BigDecimal unitPrice,*/Integer quantity,String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<QuotationItem> update(Long idQuote, Long idPremiumRate, LocalDate quoteItemDate,Long discountLoadFactor,
                                 /*BigDecimal unitPrice,*/Integer quantity,String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<QuotationItem> delete(Long idQuote, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<QuotationItem>> findAll(Long idQuotation, int page, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<List<QuotationItem>> findAll(Long idQuotation, String actionUsername);

}
