package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.PlanType;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by akipkoech on 02/02/2016.
 */
public interface ICorpService {

//    Consider returning a Result<Page<Corporate>> object;
//    Page<Corporate> getCorporates(Integer pageNumber);

/*
    void renew(Corporate corporate);
    List<Corporate> listActive();
    List<Corporate> listInactive();
    List<Corporate> listJoinedAfter(LocalDate localDate);
    List<Corporate> listJoinedBefore(LocalDate localDate);
    List<Principal> listPrincipals(Corporate corporate);
    List<CorpAnniv> listAnniversaries(Corporate corporate);
    List<Member> listMembers(Corporate corporate);
    List<Member> listActiveMembers(Corporate corporate);
    List<Category> listCategories(Corporate corporate);
*/
    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Corporate> update(
            Long idCorporate,
            String corporateName,
            String abbreviation,
            String pin,
            String tel,
            String email,
            String postalAddress,
            LocalDate joined,
            LocalDateTime lastUpdate,
            String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<Corporate> create(
            String name,
            String abbreviation,
            String pin,
            String tel,
            String email,
            String postalAddress,
            LocalDate joined,
            LocalDateTime lastUpdate, PlanType planType,
            String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS','ROLE_UW_SUPERVISORS')")
    Result<List<Corporate>> create(List<Map<String,Object>> corporateMap, String actionUsername);

    //Result<List<Corporate>> store(List<Map<String,Object>> corporateMap, String actionUsername);

    @PreAuthorize("hasRole('ROLE_uw_managers')")
    Result<Corporate> remove(Long idCorporate, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Corporate> find(Long idCorporate, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Corporate>> findAll(int page, int size,String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Corporate>> findModifiedAfter(LocalDateTime time, int page, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Corporate>> findAddedAfter(LocalDate time, int page, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Corporate>> findAddedBefore(LocalDate time, int page, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Corporate>> findByPlanType(PlanType planType, int page, int size, String actionUsername);

}
