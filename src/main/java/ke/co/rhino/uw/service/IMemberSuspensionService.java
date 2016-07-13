package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.MemberSuspension;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by akipkoech on 18/05/2016.
 */
public interface IMemberSuspensionService  {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_underwriters')")
    Result<MemberSuspension> create(LocalDate effectiveDate,
                                    String reason,
                                    Long idMember,
                                    Long idCorpAnniv,
                                    String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_underwriters')")
    Result<List<MemberSuspension>> create(List<Map<String,Object>> suspensionListMap, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_underwriters')")
    Result<MemberSuspension> update(Long idMemberSuspension,
                                    LocalDate reinstatementDate,
                                    String reason,
                                    String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_underwriters')")
    Result<List<MemberSuspension>> update(List<Map<String,Object>> suspensionListMap, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_underwriters')")
    Result<Page<MemberSuspension>> findAll(int pageNum,int size,String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_underwriters')")
    Result<List<MemberSuspension>> findByMember(Long idMember, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_underwriters')")
    Result<Page<MemberSuspension>> findByCorporate(Long idCorporate, int pageNum, int size, String actionUsername);


}
