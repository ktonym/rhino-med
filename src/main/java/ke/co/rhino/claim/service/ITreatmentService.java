package ke.co.rhino.claim.service;

import ke.co.rhino.claim.entity.Treatment;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;

/**
 * Created by user on 10/02/2017.
 */
public interface ITreatmentService {

    @PreAuthorize("hasAnyRole('ROLE_CARE_MGR','ROLE_CLM_ANALYST')")
    Result<Treatment> create(String memberNo, Long idCorpAnniv,
                             Long idCorpBenefit, Long assessmentId, LocalDate treatmentDate,
                             Long procedureId, String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_CARE_MGR','ROLE_CLM_ANALYST')")
    Result<Treatment> update(Long treatmentId, String memberNo,
                             Long idCorpAnniv, Long idCorpBenefit,
                             Long assessmentId, LocalDate treatmentDate,
                             Long procedureId, String actionUsername );

    @PreAuthorize("hasAnyRole('ROLE_CARE_MGR','ROLE_CLM_ANALYST')")
    Result<Treatment> delete(Long treatmentId, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Treatment>> findByMember(String memberNo, String actionUsername);

}
