package ke.co.rhino.claim.service;

import ke.co.rhino.claim.entity.Assessment;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by user on 07/02/2017.
 */
public interface IAssessmentService {

    @PreAuthorize("hasAnyRole('ROLE_CARE_MGR','ROLE_CLM_ANALYST')")
    Result<Assessment> create(LocalDate firstConsultationDate,
                              Long conditionId,
                              Boolean isDeceased,
                              Long idMember,
                              //String memberNo,
                              /*Long idCorpAnniv,
                              Long idCorpBenefit,
                              Long idProvider,*/
                              //String batchNo,
                              String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_CARE_MGR','ROLE_CLM_ANALYST')")
    Result<Assessment> update(Long assessmentId,
                              LocalDate firstConsultationDate,
                              Long conditionId,
                              Boolean isDeceased,
                              Long idMember,
                              //String memberNo,
                              String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_CARE_MGR','ROLE_CLM_ANALYST')")
    Result<Assessment> remove(Long assessmentId, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Assessment>> findAll(int pageNum, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Assessment>> findByAnniv(int pageNum, int size,Long idCorpAnniv, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<List<Assessment>> findByMember(Long idMember,String actionUsername);

}
