package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.BenefitType;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.MemberType;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by akipkoech on 26/05/2016.
 */
public interface ICorpBenefitService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<CorpBenefit> create(Long benefitCode,
                               BigDecimal upperLimit,
                               MemberType memberType,
                               BenefitType benefitType,
                               boolean sharing,boolean needPreAuth,
                               Integer waitingPeriod,
                               Long idParentBenefit,
                               Long idCategory,
                               String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<List<CorpBenefit>> create(List<Map<String, Object>> mapList, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<CorpBenefit> update(Long idCorpBenefit,
                               Long benefitCode,
                               BigDecimal upperLimit,
                               MemberType memberType,
                               BenefitType benefitType,
                               boolean sharing,boolean needPreAuth,
                               Integer waitingPeriod,
                               Long idParentBenefit,
                               Long idCategory,
                               String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<CorpBenefit> update(List<Map<String, Object>> mapList, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<CorpBenefit>> findAll(Long idCorporate, int pageNum, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<CorpBenefit>> findByCorpAnniv(Long idCorpAnniv, int pageNum, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<CorpBenefit>> findByCategory(Long idCategory, int pageNum, int size, String actionUsername);

    @PreAuthorize("hasRole('ROLE_UW_MANAGERS')")
    Result<CorpBenefit> remove(Long idCorpBenefit, String actionUsername);

}
