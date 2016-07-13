package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 24/11/2015.
 */
public interface MemberAnniversaryRepo extends JpaRepository<MemberAnniversary, MemberAnnivId> {

    MemberAnniversary findByCorpAnnivAndMember(CorpAnniv corpAnniv, Member member);

    @Query("SELECT ma FROM MemberAnniversary ma WHERE :dateParam BETWEEN ma.inception AND ma.expiry AND ma.member =:memberParam")
    MemberAnniversary findBy_invoiceDate_Member(@Param("dateParam")LocalDate invoiceDate,@Param("memberParam")Member member);

    Stream<MemberAnniversary> findByMember(Member member);

    Page<MemberAnniversary> findByCorpAnniv(CorpAnniv corpAnniv,Pageable pageable);

    List<MemberAnniversary> findByMember_Principal(Principal principal);

    List<MemberAnniversary> findByMember_PrincipalAndCorpAnniv(Principal principal,CorpAnniv corpAnniv);
}
