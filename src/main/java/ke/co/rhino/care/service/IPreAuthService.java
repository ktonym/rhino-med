package ke.co.rhino.care.service;

import ke.co.rhino.care.entity.PreAuth;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 26/04/2016.
 */
public interface IPreAuthService {

    @PreAuthorize("hasRole('ROLE_CARE_MGR')")
    PreAuth create(LocalDate preAuthDate,
                   String preDiagnosis,
                   BigDecimal preAuthLimit,
                   Long idServiceProvider,
                   Long idMember,
                   Long idCorpAnniv,
                   Long idCorpBenefit,
                   String actionUsername);

    @PreAuthorize("hasRole('ROLE_CARE_MGR')")
    PreAuth update(Long idPreAuth,
                   LocalDate preAuthDate,
                   String preDiagnosis,
                   BigDecimal preAuthLimit,
                   Long idServiceProvider,
                   Long idMember,
                   Long idCorpAnniv,
                   Long idCorpBenefit,
                   String actionUsername);

    @PreAuthorize("hasRole('ROLE_CARE_MGR')")
    PreAuth remove(Long idPreAuth,
                   String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Stream<PreAuth> findAll();

    @PreAuthorize("isAuthenticated()")
    Stream<PreAuth> findByPreAuthDate(LocalDate date);

    @PreAuthorize("isAuthenticated()")
    Stream<PreAuth> findByPreAuthDateBetween(LocalDate start, LocalDate end);

    @PreAuthorize("isAuthenticated()")
    Stream<PreAuth> findByProvider(Long idProvider);

    @PreAuthorize("hasRole('ROLE_CARE_MGR')")
    Stream<PreAuth> findByMember(Long idMember,Long idCorpAnniv,Long idCorpBenefit);
}
