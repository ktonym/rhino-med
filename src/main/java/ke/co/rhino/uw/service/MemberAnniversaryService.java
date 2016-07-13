package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberAnnivId;
import ke.co.rhino.uw.entity.MemberAnniversary;
import ke.co.rhino.uw.repo.CorpAnnivRepo;
import ke.co.rhino.uw.repo.MemberAnniversaryRepo;
import ke.co.rhino.uw.repo.MemberRepo;
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
import java.util.List;

/**
 * Created by akipkoech on 18/05/2016.
 */
@Service("memberAnniversaryService")
@Transactional
public class MemberAnniversaryService implements IMemberAnniversaryService {

    @Autowired
    private MemberAnniversaryRepo memberAnniversaryRepo;

    @Autowired
    private CorpAnnivRepo corpAnnivRepo;

    @Autowired
    private MemberRepo memberRepo;

    final protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<MemberAnniversary> create(Long idMember,
                                            Long idCorpAnniv,
                                            LocalDate inception,
                                            LocalDate expiry,
                                            String actionUsername){

        if(idCorpAnniv==null||idCorpAnniv<1||idMember==null||idMember<1){
            return ResultFactory.getFailResult("Member ID and Corp ID must be valid. Creation failed.");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);
        Member member = memberRepo.findOne(idMember);

        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID["+idCorpAnniv+"] was found. Creation failed.");
        }

        if(member==null){
            return ResultFactory.getFailResult("No member with ID["+idMember+"] was found. Creation failed.");
        }

        MemberAnnivId memberAnnivId = new MemberAnnivId(member,corpAnniv);

        MemberAnniversary testMemberAnniv = memberAnniversaryRepo.findOne(memberAnnivId);

        if(testMemberAnniv!=null){
            return ResultFactory.getFailResult("The member anniversary you are trying to create already exists in the system.");
        }

        if(inception==null){
            inception = corpAnniv.getInception();
        } else if(inception.isBefore(corpAnniv.getInception())) {
            return ResultFactory.getFailResult("Member's cover cannot begin before the scheme's anniversary inception date. Creation failed.");
        } else if(inception.isAfter(corpAnniv.getExpiry())){
            return ResultFactory.getFailResult("Member's cover cannot start after the scheme's expiry date. Creation failed.");
        }

        if(expiry==null){
            expiry = corpAnniv.getExpiry();
        } else if(expiry.isAfter(corpAnniv.getExpiry())){
            return ResultFactory.getFailResult("Member's cover cannot run beyond the scheme's expiry date. Creation failed.");
        } else if(expiry.isBefore(corpAnniv.getInception())){
            return ResultFactory.getFailResult("Member's cover cannot end before the scheme's anniversary inception date. Creation failed.");
        } else if(expiry.isBefore(inception)){
            return ResultFactory.getFailResult("Member's cover expiry cannot predate its inception. Creation failed.");
        } else if(expiry.isEqual(inception)){
            return ResultFactory.getFailResult("Member's cover cannot begin and end on the same day. Creation failed.");
        }

        MemberAnniversary memberAnniversary = new MemberAnniversary.MemberAnniversaryBuilder(member,corpAnniv)
                .inception(inception).expiry(expiry).build();

        memberAnniversaryRepo.save(memberAnniversary);

        return ResultFactory.getSuccessResult(memberAnniversary);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<MemberAnniversary> update(Long idMember,
                                            Long idCorpAnniv,
                                            LocalDate inception,
                                            LocalDate expiry,
                                            String actionUsername) {

        if(idCorpAnniv==null||idCorpAnniv<1||idMember==null||idMember<1){
            return ResultFactory.getFailResult("Member ID and Corp ID must be valid. Update failed.");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);
        Member member = memberRepo.findOne(idMember);

        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID["+idCorpAnniv+"] was found. Update failed.");
        }

        if(member==null){
            return ResultFactory.getFailResult("No member with ID["+idMember+"] was found. Update failed.");
        }

        MemberAnnivId memberAnnivId = new MemberAnnivId(member,corpAnniv);

        MemberAnniversary testMemberAnniv = memberAnniversaryRepo.findOne(memberAnnivId);

        if(testMemberAnniv==null){
            return ResultFactory.getFailResult("The member anniversary supplied does not exist. Update failed.");
        }

        if(inception==null){
            inception = corpAnniv.getInception();
        } else if(inception.isBefore(corpAnniv.getInception())) {
            return ResultFactory.getFailResult("Member's cover cannot begin before the scheme's anniversary inception date. Update failed.");
        } else if(inception.isAfter(corpAnniv.getExpiry())){
            return ResultFactory.getFailResult("Member's cover cannot start after the scheme's expiry date. Update failed.");
        }

        if(expiry==null){
            expiry = corpAnniv.getExpiry();
        } else if(expiry.isAfter(corpAnniv.getExpiry())){
            return ResultFactory.getFailResult("Member's cover cannot run beyond the scheme's expiry date. Update failed.");
        } else if(expiry.isBefore(corpAnniv.getInception())){
            return ResultFactory.getFailResult("Member's cover cannot end before the scheme's anniversary inception date. Update failed.");
        } else if(expiry.isBefore(inception)){
            return ResultFactory.getFailResult("Member's cover expiry cannot predate its inception. Update failed.");
        } else if(expiry.isEqual(inception)){
            return ResultFactory.getFailResult("Member's cover cannot begin and end on the same day. Update failed.");
        }

        MemberAnniversary memberAnniversary = new MemberAnniversary.MemberAnniversaryBuilder(member,corpAnniv)
                .inception(inception).expiry(expiry).build();

        memberAnniversaryRepo.save(memberAnniversary);

        return ResultFactory.getSuccessResult(memberAnniversary);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<MemberAnniversary> remove(Long idMember,
                                            Long idCorpAnniv,
                                            String actionUsername) {

        if(idCorpAnniv==null||idCorpAnniv<1||idMember==null||idMember<1){
            return ResultFactory.getFailResult("Member ID and Corp ID must be valid. Removal failed.");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);
        Member member = memberRepo.findOne(idMember);

        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID["+idCorpAnniv+"] was found. Removal failed.");
        }

        if(member==null){
            return ResultFactory.getFailResult("No member with ID["+idMember+"] was found. Removal failed.");
        }

        MemberAnnivId memberAnnivId = new MemberAnnivId(member,corpAnniv);

        MemberAnniversary memberAnniv = memberAnniversaryRepo.findOne(memberAnnivId);

        if(memberAnniv==null){
            return ResultFactory.getFailResult("Specified member anniversary does not exist. Removal failed.");
        }

        if(!memberAnniv.getBenefits().isEmpty()){
            return ResultFactory.getFailResult("The member anniversary has benefits defined. Removal failed.");
        }

        if(!memberAnniv.getMemberSuspensions().isEmpty()){
            return ResultFactory.getFailResult("The member anniversary has suspensions defined. Removal failed.");
        }

        memberAnniversaryRepo.delete(memberAnnivId);

        String msg = "Member anniversary deleted by " + actionUsername;
        logger.info(msg);
        return ResultFactory.getSuccessResult(msg);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<MemberAnniversary>> findAll(int pageNum, int size, Long idCorpAnniv,
                                                   String actionUsername) {

        if(idCorpAnniv==null||idCorpAnniv<1){
            return ResultFactory.getFailResult("Corp ID must be valid.");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);

        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found.");
        }

        PageRequest request = new PageRequest(pageNum-1,size);

        Page<MemberAnniversary> memberAnniversaries = memberAnniversaryRepo.findByCorpAnniv(corpAnniv,request);

        return ResultFactory.getSuccessResult(memberAnniversaries);
    }
}
