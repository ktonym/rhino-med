package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.CategoryPrincipal;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by akipkoech on 24/05/2016.
 */
public interface ICategoryPrincipalService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<CategoryPrincipal> create(Long idCategory,
                                     Long idPrincipal,
                                     LocalDate wef,
                                     String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<List<CategoryPrincipal>> create(List<Map<String,Object>> listMap, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<CategoryPrincipal> update(Long idCategory,
                                     Long idPrincipal,
                                     LocalDate wef,
                                     Boolean active,
                                     String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<CategoryPrincipal> remove(Long idCategory,
                                     Long idPrincipal,
                                     String actionUsername);

}
