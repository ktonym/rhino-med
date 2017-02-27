package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.MemberCategory;
import ke.co.rhino.uw.vo.Result;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 12/02/2017.
 */
public interface IMemberCategoryService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<MemberCategory> create(Long idCategory,
                                  Long idMember,
                                  LocalDate wef,
                                  String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<List<MemberCategory>> create(List<Map<String,Object>> listMap, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<MemberCategory> update(Long idCategory,
                                     Long idMember,
                                     LocalDate wef,
                                     Boolean active,
                                     String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<MemberCategory> remove(Long idCategory,
                                     Long idMember,
                                     String actionUsername);
}
