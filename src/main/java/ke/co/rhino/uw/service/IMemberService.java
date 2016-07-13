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
import java.util.stream.Stream;

/**
 * Created by akipkoech on 09/05/2016.
 */
public interface IMemberService {

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
    Result<Member> create(Long idPrincipal,
                          String firstName,
                          String surname,
                          String otherNames,
                          Sex sex,
                          LocalDate dob,
                          MemberType memberType,
                          String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
    Result<List<Member>> create(List<Map<String,Object>> memberMap,
                                  Long idPrincipal,
                                  String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
    Result<Stream<Member>> create(List<Map<String, Object>> memberMap,
                                  String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
    Result<Member> update(Long idMember,
                          String memberNo,
                          String firstName,
                          String surname,
                          String otherNames,
                          Sex sex,
                          LocalDate dob,
                          MemberType memberType,
                          String actionUsername);

    @PreAuthorize("hasAnyRole('ROLE_uw_managers','ROLE_underwriters')")
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

}
