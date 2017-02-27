package ke.co.rhino.claim.service;

import ke.co.rhino.claim.entity.Treatment;
import ke.co.rhino.claim.repo.AssessmentRepo;
import ke.co.rhino.claim.repo.TreatmentRepo;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberAnniversary;
import ke.co.rhino.uw.repo.CorpBenefitRepo;
import ke.co.rhino.uw.repo.CorpMemberBenefitRepo;
import ke.co.rhino.uw.repo.MemberAnniversaryRepo;
import ke.co.rhino.uw.repo.MemberRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Created by user on 10/02/2017.
 */
/*@Transactional
@Service("treatmentService")*/
public class TreatmentService implements ITreatmentService {

    @Autowired
    private TreatmentRepo repo;
    @Autowired
    private AssessmentRepo assessmentRepo;
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private CorpMemberBenefitRepo cmbRepo;
    @Autowired
    private MemberAnniversaryRepo memberAnniversaryRepo;
    @Autowired
    private CorpBenefitRepo corpBenefitRepo;

    @Override
    public Result<Treatment> create(String memberNo, Long idCorpAnniv,
                                    Long idCorpBenefit, Long assessmentId, LocalDate treatmentDate,
                                    Long procedureId, String actionUsername) {


        CorpBenefit corpBenefit = corpBenefitRepo.findOne(idCorpBenefit);
        Member member = memberRepo.findByMemberNo(memberNo);
        MemberAnniversary ma = memberAnniversaryRepo.findBy_invoiceDate_Member(treatmentDate, member);
        // CorpMemberBenefit corpMemberBenefit = cmbRepo.findByMemNo_InvcDt_Benefit(idCorpBenefit,memberNo,invoiceDate);
        CorpMemberBenefit corpMemberBenefit = cmbRepo.findByMemberAnnivAndBenefit(ma,corpBenefit);

        if(corpMemberBenefit==null){
            return ResultFactory.getFailResult("No member benefit exists for the parameters given. Update failed.");
        }

        return null;
    }

    @Override
    public Result<Treatment> update(Long treatmentId, String memberNo, Long idCorpAnniv, Long idCorpBenefit,
                                    Long assessmentId, LocalDate treatmentDate, Long procedureId, String actionUsername) {
        return null;
    }

    @Override
    public Result<Treatment> delete(Long treatmentId, String actionUsername) {
        return null;
    }

    @Override
    public Result<Page<Treatment>> findByMember(String memberNo, String actionUsername) {
        return null;
    }
}
