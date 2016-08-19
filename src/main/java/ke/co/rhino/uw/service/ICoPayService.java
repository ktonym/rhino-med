package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.CoPay;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;

/**
 * Created by akipkoech on 19/08/2016.
 */
public interface ICoPayService {

    @PreAuthorize("hasAnyRole('ROLE_UW_SUPERVISORS','ROLE_UNDERWRITERS')")
    Result<CoPay> create(Long idServiceProvider,
                         Long idRegulation,
                         BigDecimal coPayAmount,
                         String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_SUPERVISORS','ROLE_UNDERWRITERS')")
    Result<CoPay> update(Long idCoPay,
                         Long idServiceProvider,
                         Long idRegulation,
                         BigDecimal coPayAmount,
                         String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_SUPERVISORS','ROLE_UW_MANAGERS')")
    Result<CoPay> delete(Long idCoPay, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<CoPay>> findAll(int page,int size);

    @PreAuthorize("isAuthenticated()")
    Result<Page<CoPay>> findByCorporate(Long idCorporate,int page,int size);

}
