package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberType;
import ke.co.rhino.uw.entity.Principal;
import ke.co.rhino.uw.entity.Sex;
import ke.co.rhino.uw.repo.MemberRepo;
import ke.co.rhino.uw.repo.PrincipalRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 09/05/2016.
 */
@Service("memberService")
@Transactional
public class MemberService implements IMemberService {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private PrincipalRepo principalRepo;

    final protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Member> create(Long idPrincipal,
                                 String firstName,
                                 String surname,
                                 String otherNames,
                                 Sex sex,
                                 LocalDate dob,
                                 MemberType memberType,
                                 String actionUsername){

        Principal principal = principalRepo.findOne(idPrincipal);

        if(principal==null){
            return ResultFactory.getFailResult("Could not find Principal with ID ["+idPrincipal+"]. Member creation failed.");
        }

        if(firstName.equals("")||firstName.trim().length()==0||surname.equals("")||surname.trim().length()==0){
            return ResultFactory.getFailResult("First name or surname cannot be blank. Member creation failed.");
        }

        if(sex==null){ return ResultFactory.getFailResult("Sex cannot be null. Member creation failed."); }

        if(memberType==null){ return ResultFactory.getFailResult("Member type cannot be null. Member creation failed."); }

        if(dob.isAfter(LocalDate.now())){
            return ResultFactory.getFailResult("Date of birth cannot be in the future. Member creation failed.");
        }

        String memberNo = generateMemberNo(principal);

        Member member = new Member.MemberBuilder(firstName,surname,memberNo,sex,dob,memberType)
                .principal(principal)
                .otherNames(otherNames).build();

        memberRepo.save(member);

        return ResultFactory.getSuccessResult(member);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<List<Member>> create(List<Map<String,Object>> memberMap,
                                         Long idPrincipal,
                                         String actionUsername) {

        if(idPrincipal==null){
            return ResultFactory.getFailResult("Could not add members with an invalid Principal ID.");
        }

        Principal principal = principalRepo.findOne(idPrincipal);

        if(principal==null){
            return ResultFactory.getFailResult("Could not find Principal with ID ["+idPrincipal+"]. Member creation failed.");
        }

        List<Member> members = new ArrayList<>();

        StringBuilder errs = new StringBuilder();
        long cnt = memberRepo.countByPrincipal(principal);
        String prefix = principal.getFamilyNo();

        for(Map map: memberMap){

            String firstName = String.valueOf(map.get("firstName"));
            String surname = String.valueOf(map.get("surname"));
            Sex sex = (Sex) map.get("sex");
            LocalDate dob = (LocalDate) map.get("dob");
            MemberType memberType = (MemberType) map.get("memberType");
            String otherNames = String.valueOf(map.get("otherNames"));

            if(firstName.equals("")||firstName.trim().length()==0||surname.equals("")||surname.trim().length()==0){
                errs.append("First name or surname cannot be blank. Member creation failed.\n");
                //TODO escape this loop here..
                break;
            }

            if(sex==null){
                errs.append("Sex cannot be null. Member creation failed.\n");
                //escape this loop here..
                break;
            }

            if(memberType==null){
                errs.append("Member type cannot be null. Member creation failed.\n");
                //escape this loop here..
                break;
            }

            if(dob.isAfter(LocalDate.now())){
                errs.append("Date of birth cannot be in the future. Member creation failed.\n");
                //escape this loop here..
                break;
            }

            //Building a new member number
            StringBuilder newNumBuilder = new StringBuilder(prefix);
            if(cnt<10){
                newNumBuilder.append("/0").append(cnt);
            } else {
                newNumBuilder.append("/").append(cnt);
            }
            cnt++;
            String memNum = newNumBuilder.toString();

            Member.MemberBuilder memberBuilder = new Member.MemberBuilder(firstName,surname,memNum,sex,dob,memberType)
                    .principal(principal);

            if(!otherNames.trim().isEmpty()){
                memberBuilder.otherNames(otherNames);
            }

            Member member = memberBuilder.build();
            members.add(member);

        }

        if(members.isEmpty()){
            return ResultFactory.getFailResult(errs.toString());
        }

        memberRepo.save(members);

        return ResultFactory.getSuccessResult(members,errs.toString());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Stream<Member>> create(List<Map<String, Object>> memberMap,
                                         String actionUsername) {

        return null;

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Member> update(Long idMember,
                                 String memberNo,
                                 String firstName,
                                 String surname,
                                 String otherNames,
                                 Sex sex,
                                 LocalDate dob,
                                 MemberType memberType,
                                 String actionUsername) {

        if(idMember==null || idMember==0){
            return ResultFactory.getFailResult("Cannot update member with null ID.");
        }

        Member memberFromId = memberRepo.getOne(idMember);

        if(memberFromId==null){
            return ResultFactory.getFailResult("Member with ID ["+idMember+"] is not valid. Update failed.");
        }

        Member memberFromMemNo = memberRepo.findByMemberNo(memberNo);

        if(memberFromMemNo == null){
            return ResultFactory.getFailResult("Member number supplied ["+ memberNo+"] is invalid. Update failed.");
        }

        if(memberFromMemNo.getId()!=idMember){
            return ResultFactory.getFailResult("Member number does not match the ID ["+idMember+"]. Update failed.");
        }

        if(dob.isAfter(LocalDate.now())){
            return ResultFactory.getFailResult("Date of birth cannot be in the future. Member creation failed.");
        }

        Member member = new Member.MemberBuilder(firstName,surname,memberNo,sex,dob,memberType)
                .otherNames(otherNames).build();

        memberRepo.save(member);

        return ResultFactory.getSuccessResult(member);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Member> remove(Long idMember, String actionUsername) {

        if(idMember==null || idMember==0){
            return ResultFactory.getFailResult("Cannot remove member with null ID.");
        }

        Member member = memberRepo.findOne(idMember);

        if(member==null){
            return ResultFactory.getFailResult("Member is invalid. Deletion failed.");
        }

        if(member.getMemberAnniversaries().size()>0){
            return ResultFactory.getFailResult("Member has anniversaries defined. Deletion failed.");
        }

        memberRepo.delete(member);

        String msg = "Member with ID [" +idMember+ "] was deleted by " + actionUsername;

        logger.info(msg);

        return ResultFactory.getSuccessResult(msg);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Member>> findActive(int pageNum, int size,String actionUsername) {

        PageRequest pageRequest = new PageRequest(pageNum-1,size, Sort.Direction.ASC,"surname");

        Page<Member> memberPage = memberRepo.findActive(pageRequest);

        return ResultFactory.getSuccessResult(memberPage);

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Member>> findAll(int pageNum, int size, String actionUsername) {

        PageRequest pageRequest = new PageRequest(pageNum-1,size, Sort.Direction.ASC,"surname");

        Page<Member> memberPage = memberRepo.findAll(pageRequest);

        return ResultFactory.getSuccessResult(memberPage);
    }

    @Override
    public Result<Page<Member>> findAllCovered(int pageNum, int size, String actionUsername) {

        PageRequest pageRequest = new PageRequest(pageNum-1,size, Sort.Direction.ASC,"surname");

        Page<Member> memberPage = memberRepo.findAllCovered(pageRequest);

        return ResultFactory.getSuccessResult(memberPage);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<Member>> findByPrincipal(Long idPrincipal, String actionUsername) {

        if(idPrincipal==null || idPrincipal<1){
            return ResultFactory.getFailResult("Invalid Principal ID ["+idPrincipal+"] supplied.");
        }

        Principal principal = principalRepo.findOne(idPrincipal);

        if(principal==null){
            return ResultFactory.getFailResult("No principal with ID ["+idPrincipal+"] was found.");
        }

        List<Member> memberList = memberRepo.findByPrincipal(principal);

        if(memberList.isEmpty()){
            return ResultFactory.getFailResult("Principal ["+principal.getFamilyNo()+"] has no dependants yet.");
        }

        return ResultFactory.getSuccessResult(memberList);

    }

    private String generateMemberNo(Principal principal){

        String prefix = principal.getFamilyNo();

        long newNum = memberRepo.countByPrincipal(principal);
        String newNumStr = String.valueOf(newNum);
        int i = newNumStr.length();
        StringBuilder strBuilder = new StringBuilder(newNumStr);
        if (i<2){
            strBuilder.insert(0,"0");
        }

        return strBuilder.insert(0,"/").insert(0,prefix).toString();
    }
}
