package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.BenefitRef;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Created by akipkoech on 10/05/2016.
 */
public interface IBenefitRefService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<BenefitRef> create(String benefitName,
                              String description,
                              String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<BenefitRef> update(Long benefitCode,
                             String benefitName,
                             String description,
                             String actionUsername);

    @PreAuthorize("hasRole('ROLE_UW_MANAGERS')")
    Result<BenefitRef> remove(Long benefitCode,
                              String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<BenefitRef> find(Long benefitCode,
                            String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<BenefitRef>> findAll(int pageNum,int size,String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<BenefitRef>> search(int pageNum,int size,
                                    String searchStr,
                                    String actionUsername);

}
