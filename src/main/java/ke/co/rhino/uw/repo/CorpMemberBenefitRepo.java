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
 * Created by akipkoech on 03/05/2016.
 */
public interface CorpMemberBenefitRepo extends JpaRepository<CorpMemberBenefit, CorpMemberBenefitId> {

    Page<CorpMemberBenefit> findByMemberAnniv(MemberAnniversary memberAnniversary, Pageable pageable);

    Stream<CorpMemberBenefit> findByBenefit(CorpBenefit corpBenefit);

//    @Query("SELECT cmb FROM CorpMemberBenefit cmb WHERE cmb.benefit = (SELECT cb FROM CorpBenefit cb WHERE cb.idCorpBenefit = :idCorpBenefit) " +
//            "AND cmb.memberAnniv = (SELECT ma FROM MemberAnniversary ma WHERE ma.member = (SELECT m FROM Member m where m.memberNo = :memberNo) " +
//                                        "AND ma.corpAnniv = (SELECT ca FROM CorpAnniv ca WHERE :invoiceDate BETWEEN ca.inception AND ca.expiry ))")
//    CorpMemberBenefit findByMemNo_InvcDt_Benefit(@Param("idCorpBenefit") Long idCorpBenefit,
//                                                         @Param("memberNo") String memberNo,
//                                                         @Param("invoiceDate")LocalDate invoiceDate);

    CorpMemberBenefit findByMemberAnnivAndBenefit(MemberAnniversary memberAnniversary,CorpBenefit corpBenefit);

    @Query("UPDATE CorpMemberBenefit cmb SET cmb.status = :status " +
            "WHERE cmb.benefit = :corpBenefit AND cmb.memberAnniv = :memberAnniv" )
    int updateStatus(@Param("status")BenefitStatus status,@Param("corpBenefit") CorpBenefit corpBenefit,@Param("memberAnniv") MemberAnniversary memberAnniversary);


//    @Query("SELECT cmb FROM CorpMemberBenefit WHERE cmb.corpBenefit = (SELECT cb FROM CorpBenefit cb WHERE cb.idCorpBenefit = :idCorpBenefit" +
//                                                                        " AND cb )")
//    List<CorpMemberBenefit> findChildMemberBenefits(@Param("idCorpBenefit") Long idCorpBenefit);

}
