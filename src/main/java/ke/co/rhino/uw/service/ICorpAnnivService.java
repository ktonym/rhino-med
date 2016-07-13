package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.vo.Result;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 03/02/2016.
 */
public interface ICorpAnnivService {

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
    Result<CorpAnniv> create(
            Long idCorporate,
            //Long idCorpAnniv,
            Long idIntermediary,
            Integer anniv,
            LocalDate inception,
            LocalDate expiry,
            LocalDate renewalDate,
            String actionUsername
    );

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
    Result<CorpAnniv> update(
            Long idCorporate,
            Long idCorpAnniv,
            Long idIntermediary,
            Integer anniv,
            LocalDate inception,
            LocalDate expiry,
            LocalDate renewalDate,
            String actionUsername
    );

    @PreAuthorize("hasRole('ROLE_uw_managers')")
    Result<CorpAnniv> remove(Long idCorpAnniv, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<CorpAnniv> find(Long idCorpAnniv, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<List<CorpAnniv>> findAll(String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<List<CorpAnniv>> findByCorporate(Long idCorporate, String actionUsername);

}
