package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Regulation;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by user on 15-Aug-16.
 */
public interface IRegulationService {

    @PreAuthorize("hasAnyRole('ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<Regulation> create(Long idCorpAnniv, Boolean coPay, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UW_SUPERVISORS')")
    Result<Regulation> update(Long idRegulation, Long idCorpAnniv, Integer commRate, Integer whTaxRate,
                              Boolean coPay, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UW_SUPERVISORS')")
    Result<Regulation> delete(Long idRegulation, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Regulation> findOne(Long idRegulation, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Regulation> findByCorpAnniv(Long idCorpAnniv, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Regulation>> findByCorporate(Long idCorporate, int page, int size, String actionUsername);

}
