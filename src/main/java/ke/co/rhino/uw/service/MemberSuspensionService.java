package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.*;
import ke.co.rhino.uw.repo.*;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by akipkoech on 19/05/2016.
 */
@Service("memberSuspensionService")
@Transactional
public class MemberSuspensionService implements IMemberSuspensionService {

    @Autowired
    private MemberSuspensionRepo memberSuspensionRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private CorporateRepo corporateRepo;

    @Autowired
    private CorpAnnivRepo corpAnnivRepo;

    @Autowired
    private MemberAnniversaryRepo memberAnniversaryRepo;

    final protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<MemberSuspension> create(LocalDate effectiveDate,
                                           String reason,
                                           Long idMember,
                                           Long idCorpAnniv, String actionUsername) {

        if(effectiveDate==null){
            return ResultFactory.getFailResult("Member suspension must have a valid effective date.");
        }

        if(idMember==null||idCorpAnniv==null){
            return ResultFactory.getFailResult("Member suspension must have a valid member and anniversary");
        }

        if(reason.trim().isEmpty()){
            return ResultFactory.getFailResult("Please supply a valid reason");
        }

        Member member = memberRepo.findOne(idMember);

        if(member==null){
            return ResultFactory.getFailResult("No member with ID was found. Cannot suspend.");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);

        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found. Cannot suspend.");
        }

        MemberAnnivId memberAnnivId = new MemberAnnivId(member,corpAnniv);

        MemberAnniversary memberAnniv = memberAnniversaryRepo.findOne(memberAnnivId);

        if(memberAnniv==null){
            return ResultFactory.getFailResult("The member anniversary does not exist.");
        }

        MemberSuspension memSusp = new MemberSuspension();

        memSusp.setEffectiveDate(effectiveDate);

        memSusp.setMemberAnniv(memberAnniv);

        memSusp.setReason(reason);

        memberSuspensionRepo.save(memSusp);

        return ResultFactory.getSuccessResult(memSusp);

    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<List<MemberSuspension>> create(List<Map<String, Object>> suspensionListMap, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<MemberSuspension> update(Long idMemberSuspension,
                                           LocalDate reinstatementDate,
                                           String reason, String actionUsername) {

        if(idMemberSuspension==null || idMemberSuspension<1){
            return ResultFactory.getFailResult("Invalid member suspension ID supplied. Update failed.");
        }

        MemberSuspension memSusp = memberSuspensionRepo.findOne(idMemberSuspension);

        if(memSusp==null){
            return ResultFactory.getFailResult("No member suspension with ID ["+idMemberSuspension+"] was found. Update failed.");
        }

        if(reinstatementDate==null){
            if(reason.trim().isEmpty()||reason==null) {
                return ResultFactory.getFailResult("Reinstatement date or reason must be supplied. Update failed.");
            } else {
                memSusp.setReason(reason);
                memberSuspensionRepo.save(memSusp);
                return ResultFactory.getSuccessResult(memSusp);
            }
        }

        memSusp.setReinstatementDate(reinstatementDate);
        memberSuspensionRepo.save(memSusp);
        return ResultFactory.getSuccessResult(memSusp);

    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<List<MemberSuspension>> update(List<Map<String, Object>> suspensionListMap, String actionUsername) {

        List<MemberSuspension> suspensions = new ArrayList<>();
        StringBuilder errs = new StringBuilder();

        for(Map map: suspensionListMap){
            Long idMemberSuspension = (Long) map.get("idMemberSuspension");
            LocalDate reinstatementDate = (LocalDate) map.get("reinstatementDate");
            String reason = String.valueOf(map.get("reason"));

            if(idMemberSuspension == null || idMemberSuspension<1){
                errs.append("Invalid suspension ID supplied.");
                break;
            }

            MemberSuspension memSusp = memberSuspensionRepo.findOne(idMemberSuspension);

            if(memSusp==null){
                break;
            }

            if(reinstatementDate==null){
                if(reason.trim().isEmpty()||reason==null) {
                    errs.append("Reinstatement date or reason must be supplied. Update failed.\n");
                } else {
                    memSusp.setReason(reason);
                }
            } else {
                if(memSusp.getEffectiveDate().isAfter(reinstatementDate)){
                    errs.append("Reinstatement date cannot predate effective date. Update failed.\n");
                    break;
                }
                memSusp.setReinstatementDate(reinstatementDate);
                if(!reason.trim().isEmpty()&&reason!=null){
                    memSusp.setReason(reason);
                }
            }

            suspensions.add(memSusp);

        }
        memberSuspensionRepo.save(suspensions);
        return ResultFactory.getSuccessResult(suspensions,errs.toString());
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<MemberSuspension>> findAll(int pageNum, int size, String actionUsername) {

        PageRequest request = new PageRequest(pageNum-1,size);

        Page<MemberSuspension> page = memberSuspensionRepo.findAll(request);

        return ResultFactory.getSuccessResult(page);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<List<MemberSuspension>> findByMember(Long idMember, String actionUsername) {

        if (idMember==null || idMember<1){
            return ResultFactory.getFailResult("Invalid member ID was supplied.");
        }

        Member member = memberRepo.findOne(idMember);

        if(member==null){
            return ResultFactory.getFailResult("No member with ID ["+idMember+"] found.");
        }

        List<MemberSuspension> memberSuspensions = memberSuspensionRepo.findByMemberAnniv_Member(member);

        return ResultFactory.getSuccessResult(memberSuspensions);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<MemberSuspension>> findByCorporate(Long idCorporate, int pageNum, int size, String actionUsername) {

        if (idCorporate==null||idCorporate<1){
            return ResultFactory.getFailResult("Invalid corporate ID was supplied.");
        }

        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate==null){
            return ResultFactory.getFailResult("No corporate with ID ["+idCorporate+"] was found.");
        }

        PageRequest request = new PageRequest(pageNum-1,size);

        Page<MemberSuspension> page = memberSuspensionRepo.findByMemberAnniv_CorpAnniv_Corporate(corporate, request);

        return ResultFactory.getSuccessResult(page);
    }
}
