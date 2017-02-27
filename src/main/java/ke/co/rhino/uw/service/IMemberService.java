package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberType;
import ke.co.rhino.uw.entity.Sex;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 09/05/2016.
 */
public interface IMemberService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Member> create(Long idCorporate, Optional<Long> idPrincipal,
                          String firstName,
                          String surname,
                          String otherNames,
                          Sex sex,
                          LocalDate dob,
                          MemberType memberType,
                          String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<List<Member>> create(List<Map<String,Object>> memberMap,
                                  Long idPrincipal,Long idCorporate,
                                  String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Stream<Member>> create(List<Map<String, Object>> memberMap,
                                  String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Member> update(Long idMember,
                          String memberNo,
                          String firstName,
                          String surname,
                          String otherNames,
                          Sex sex,
                          LocalDate dob,
                          MemberType memberType,Long idCorporate,
                          String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    Result<Member> remove(Long idMember,String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Member>> findActive(int pageNum, int size,String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Member>> findAll(int pageNum, int size,String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Member>> findAllCovered(int pageNum, int size, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<List<Member>> findByPrincipal(Long idPrincipal,
                                           String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<Page<Member>> findByCorpAnniv(int pageNum, int size, Long idCorpAnniv,
                                         String actionUsername);

    @PreAuthorize("isAuthenticated()")
    Result<List<Member>> findPrincipals(Long idCorporate, String actionUsername);
}
