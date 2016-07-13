package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberSuspension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by akipkoech on 18/05/2016.
 */
public interface MemberSuspensionRepo extends JpaRepository<MemberSuspension,Long>{

    List<MemberSuspension> findByMemberAnniv_Member(Member member);

    Page<MemberSuspension> findByMemberAnniv_CorpAnniv(CorpAnniv anniv, Pageable pageable);

    Page<MemberSuspension> findByMemberAnniv_Member_Principal_Corporate(Corporate corporate, Pageable pageable);

    //Created this because it is shorter than the one above.
    Page<MemberSuspension> findByMemberAnniv_CorpAnniv_Corporate(Corporate corporate,Pageable pageable);
}
