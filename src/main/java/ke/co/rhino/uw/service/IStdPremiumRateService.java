package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.PremiumType;
import ke.co.rhino.uw.entity.StdPremiumRate;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by user on 10/04/2017.
 */
public interface IStdPremiumRateService {

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<StdPremiumRate> create(BigDecimal upperLimit,BigDecimal premium,
                                  String familySize,Long benefitCode,String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<StdPremiumRate> update(Long idPremiumRate,
                                  /*PremiumType premiumType,*/BigDecimal upperLimit,
                                  BigDecimal premium, String familySize,
                                  Long benefitCode,String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<Page<StdPremiumRate>> findAll(int page, int size, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<Page<StdPremiumRate>> findByBenefit(int page, int size,Long benefitCode, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<Page<StdPremiumRate>> findByFamilySize(int page, int size, String familySize, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<List<StdPremiumRate>> findByLimitSizeAndBenefit(BigDecimal upperLimit,String familySize,
                                                           Long benefitCode, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<List<StdPremiumRate>> findByPremiumSizeAndBenefit(BigDecimal premium,String familySize,
                                                             Long benefitCode, String actionUsername);
}
