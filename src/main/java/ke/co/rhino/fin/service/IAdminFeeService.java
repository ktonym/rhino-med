package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.AdminFee;
import ke.co.rhino.fin.entity.AdminFeeType;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;

/**
 * Created by akipkoech on 19/07/2016.
 */
public interface IAdminFeeService {

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<AdminFee> create(Long idFundInvoice,AdminFeeType adminFeeType,BigDecimal amount, String actionUsername);
    @PreAuthorize("isAuthenticated()")
    Result<Page<AdminFee>> findAll(Long idCorpBenefit,int pageNo, int size,String actionUsername);
    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<AdminFee> update(Long idAdminFee,Long idFundInvoice,AdminFeeType adminFeeType,BigDecimal amount,String actionUsername);
    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<AdminFee> remove(Long idAdminFee,String actionUsername);

}
