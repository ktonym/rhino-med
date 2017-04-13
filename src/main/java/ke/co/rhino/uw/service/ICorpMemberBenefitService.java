package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.BenefitStatus;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by akipkoech on 13/06/2016.
 */
public interface ICorpMemberBenefitService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<CorpMemberBenefit> create(Long idMember,
                                     Long idCorpAnniv,
                                     Long idCorpBenefit,
                                     Optional<Long> idParentCorpBenefitOpt,
                                     BenefitStatus status,
                                     LocalDate wef,
                                     String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<CorpMemberBenefit>> findAll(Long idMember,
                                            Long idCorpAnniv, int pageNum, int size,
                                            String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<CorpMemberBenefit> update(Long idCorpMemberBenefit,
                                     Long idMember,
                                     Long idCorpAnniv,
                                     Long idCorpBenefit,
                                     Optional<Long> idParentCorpBenefitOpt,
                                     BenefitStatus status,
                                     LocalDate wef,
                                     String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<CorpMemberBenefit> delete(/*Long idMember,
                                     Long idCorpAnniv,
                                     Long idCorpBenefit,*/
                                     Long idCorpMemberBenefit,
                                     String actionUsername);

}
