package ke.co.rhino.claim.service;

import ke.co.rhino.claim.entity.Assessment;
import ke.co.rhino.claim.entity.Condition;
import ke.co.rhino.claim.repo.AssessmentRepo;
import ke.co.rhino.claim.repo.ConditionRepo;
import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.repo.MemberRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by user on 08/02/2017.
 */

/*@Transactional
@Service("assessmentService")*/
public class AssessmentService implements IAssessmentService {

    @Autowired
    private AssessmentRepo repo;

    @Autowired
    private ConditionRepo conditionRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Assessment> create(LocalDate firstConsultationDate, Long conditionId,
                                     Boolean isDeceased, Long idMember, String actionUsername) {

        if(conditionId==null||conditionId<1){
            return ResultFactory.getFailResult("Invalid condition provided. Cannot save assessment.");
        }
        if(idMember==null||idMember<1){
            return ResultFactory.getFailResult("Invalid member provided. Cannot save assessment.");
        }
        if(isDeceased==null){
            isDeceased = false;
        }

        if(firstConsultationDate==null){
            return ResultFactory.getFailResult("First consultation date missing. Cannot save assessment.");
        }
        if(firstConsultationDate.isAfter(LocalDate.now())){
            return ResultFactory.getFailResult("First consultation cannot be in the future. Save failed.");
        }
        if(!getMember(idMember).isPresent()){
            return ResultFactory.getFailResult("No member with ID ["+idMember+"] exists in the system.");
        }

        if(!getCondition(conditionId).isPresent()){
            return ResultFactory.getFailResult("No condition with ID ["+conditionId+"] exists in the system. Save failed.");
        }

        Assessment assessment = new Assessment.AssessmentBuilder()
                .condition(getCondition(conditionId).get())
                .member(getMember(idMember).get())
                .firstConsultation(firstConsultationDate)
                .isDeceased(isDeceased)
                .build();

        repo.save(assessment);

        return null;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Assessment> update(Long assessmentId, LocalDate firstConsultationDate,
                                     Long conditionId, Boolean isDeceased, Long idMember, String actionUsername) {

        if(assessmentId==null||assessmentId<1){
            return ResultFactory.getFailResult("Invalid assessment ID provided. Cannot update.");
        }
        if(conditionId==null||conditionId<1){
            return ResultFactory.getFailResult("Invalid condition provided. Cannot save assessment.");
        }


        Optional<Assessment> assessmentOpt = repo.getOne(assessmentId);

        if(!assessmentOpt.isPresent()){
            return ResultFactory.getFailResult("No assessment with ID["+assessmentId+"] was found in the system. Update failed.");
        }


        Assessment.AssessmentBuilder builder = new Assessment.AssessmentBuilder()
                .assessmentId(assessmentId);

        if(idMember != null && idMember >0){
            Member member = memberRepo.findOne(idMember);
            if(member==null){
                return ResultFactory.getFailResult("Invalid member ID provided. Update failed.");
            }
            List<Assessment> assessmentsByMember = repo.findByMember(member);
            boolean match = assessmentsByMember.stream()
                    .anyMatch(a -> a.equals(assessmentOpt.get()));
            if(!match){
                return ResultFactory.getFailResult("Assessment does not belong to member supplied. Update failed.");
            }
            builder.member(member);
        }

        Optional<Condition> conditionOpt = conditionRepo.getOne(conditionId);
        if(conditionOpt.isPresent()){
            builder.condition(conditionOpt.get());
        }

        if(firstConsultationDate != null){
            if(firstConsultationDate.isAfter(LocalDate.now())){
                return ResultFactory.getFailResult("First consultation cannot be in the future. Update failed.");
            }
            builder.firstConsultation(firstConsultationDate);
        }

        Assessment assessment = builder.build();

        repo.save(assessment);

        return ResultFactory.getSuccessResult(assessment);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Assessment> remove(Long assessmentId, String actionUsername) {

        if(assessmentId==null||assessmentId<1){
            return ResultFactory.getFailResult("Invalid assessment ID provided. Delete failed.");
        }

        Optional<Assessment> assessmentOpt = repo.getOne(assessmentId);

        if(assessmentOpt.isPresent()){
            repo.delete(assessmentId);
            return ResultFactory.getSuccessResult("Assessment successfully deleted by"+actionUsername);
        }
        return ResultFactory.getFailResult("No assessment with ID["+assessmentId+"] was found. Delete failed.");

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Assessment>> findAll(int pageNum, int size, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Assessment>> findByAnniv(int pageNum, int size, Long idCorpAnniv, String actionUsername) {
        return null;
    }

    @Override
    public Result<List<Assessment>> findByMember(Long idMember, String actionUsername) {
        if(idMember==null||idMember<1){
            return ResultFactory.getFailResult("Invalid member ID provided");
        }
        Optional<Member> memberOptional = memberRepo.getOne(idMember);
        if(memberOptional.isPresent()){
            List<Assessment> assessments = repo.findByMember(memberOptional.get());
            return ResultFactory.getSuccessResult(assessments);
        }
        return ResultFactory.getFailResult("No member with ID["+idMember+"] was found.");
    }

    private Optional<Condition> getCondition(Long conditionId){
        Optional<Condition> conditionOpt = conditionRepo.getOne(conditionId);
        if(conditionOpt.isPresent()){
            return conditionOpt;
        }
        return Optional.empty();
    }

    private Optional<Member> getMember(Long memberId){
        Optional<Member> memberOptional = memberRepo.getOne(memberId);
        if(memberOptional.isPresent()){
            return memberOptional;
        }
        return Optional.empty();
    }

}
