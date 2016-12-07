package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Principal;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;


import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 05/05/2016.
 */
public interface IPrincipalService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Principal> create(//String familyNo,
                             String firstName,
                             String surname,
                             String otherNames,
                             LocalDate dob,
                             Long idCorporate,
                             String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Principal> update(Long idPrincipal,
                             String familyNo,
                             String firstName,
                             String surname,
                             String otherNames,
                             LocalDate dob,
                            // Long idCorporate, //TODO in future, this may be necessary: when changing the corporate that a principal belongs to!!
                             String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Principal> remove(Long idPrincipal, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Stream<Principal>> findAll(String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Principal>> findByCorporate(Long idCorporate, Integer pageNum, Integer size, String actionUsername);

    Result<Page<Principal>> findByCorpAnniv(Long idCorpAnniv, Integer pageNo, Integer size, String actionUsername);
}
