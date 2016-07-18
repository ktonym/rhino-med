package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.GroupRate;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by akipkoech on 11/05/2016.
 */
public interface IGroupRateService {

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
    Result<GroupRate> create(Long idCorporate,
                             Long benefitCode,
                             String famSize,
                             BigDecimal upperLimit,
                             BigDecimal premium,
                             String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
    Result<Page<GroupRate>> create(List<Map<String,Object>> mapList,
                                   String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
    Result<GroupRate> update(Long idPremiumRate,
                             Long idCorporate,
                             Long benefitCode,
                             String famSize,
                             BigDecimal upperLimit,
                             BigDecimal premium,
                             String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_uw_supervisor')")
    Result<GroupRate> remove(Long idPremiumRate, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<GroupRate>> findAll(Integer pageNumber, Integer size,String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<GroupRate>> findByCorporate(Long idCorporate,
                                            Integer pageNumber,
                                            Integer pageSize,
                                            String actionUsername);

}
