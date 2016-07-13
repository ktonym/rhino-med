package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.MemberAnniversary;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 17/05/2016.
 */
public interface IMemberAnniversaryService {

    @PreAuthorize("hasRole('ROLE_underwriters')")
    Result<MemberAnniversary> create(Long idMember,
                                     Long idCorpAnniv,
                                     LocalDate inception,
                                     LocalDate expiry,
                                     String actionUsername);

    @PreAuthorize("hasRole('ROLE_underwriters')")
    Result<MemberAnniversary> update(Long idMember,
                                     Long idCorpAnniv,
                                     LocalDate inception,
                                     LocalDate expiry,
                                     String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS')")
    Result<MemberAnniversary> remove(Long idMember,
                                     Long idCorpAnniv,
                                     String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<MemberAnniversary>> findAll(int pageNum,int size,Long idCorpAnniv,
                                            String actionUsername);



}
