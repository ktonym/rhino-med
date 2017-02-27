package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.*;
import ke.co.rhino.uw.repo.CorpAnnivRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.repo.MemberRepo;
import ke.co.rhino.uw.repo.PrincipalRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
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
    private CorpAnnivRepo corpAnnivRepo;
    @Autowired
    private CorporateRepo corporateRepo;

    final protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Member> create(Long idCorporate, Optional<Long> idPrincipalOpt,
                                 String firstName,
                                 String surname,
                                 String otherNames,
                                 Sex sex,
                                 LocalDate dob,
                                 MemberType memberType,
                                 String actionUsername){


        if(idCorporate==null){
            return ResultFactory.getFailResult("Null corporate ID ["+idCorporate+"] supplied. Principal creation failed");
        }
        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate==null){
            return ResultFactory.getFailResult("No corporate with ID ["+idCorporate+"] exists. Principal creation failed.");
        }

        if(firstName.equals("")||firstName.trim().length()==0||surname.equals("")||surname.trim().length()==0){
            return ResultFactory.getFailResult("First name or surname cannot be blank. Member creation failed.");
        }

        if(sex==null){ return ResultFactory.getFailResult("Sex cannot be null. Member creation failed."); }

        if(memberType==null){ return ResultFactory.getFailResult("Member type cannot be null. Member creation failed."); }

        if(dob.isAfter(LocalDate.now())){
            return ResultFactory.getFailResult("Date of birth cannot be in the future. Member creation failed.");
        }

        Member.MemberBuilder builder = new Member.MemberBuilder(firstName,surname,sex,dob,memberType,corporate)
                .otherNames(otherNames);

        String memberNo;

        if(idPrincipalOpt.isPresent()){ //we're creating a dependant
            Long idPrincipal = idPrincipalOpt.get();
            Member principal = memberRepo.findOne(idPrincipal);
            if(principal==null){
                return ResultFactory.getFailResult("Could not find Principal with ID ["+idPrincipal+"]. Member creation failed.");
            }
            // Check whether Principal is at the highest level
            if(principal.getPrincipal()!=null){ //will it generate an expired transaction exception?
                return ResultFactory.getFailResult("Principal supplied is a dependant. Member creation failed.");
            }

            memberNo = generateMemberNo(principal);
            builder.principal(principal);
        } else { //we're creating a principal
            //Need to check that they are above 18yrs
            if(Period.between(dob,LocalDate.now()).getYears()<18){
                return ResultFactory.getFailResult("The principal must be at least 18 years. Member creation failed.");
            }
            Map<String,String> famMap = generatePrincipalDtl(corporate);
            memberNo = famMap.get("memNum");
        }

        builder.memberNo(memberNo);

        Member member = builder.build();

        memberRepo.save(member);

        return ResultFactory.getSuccessResult(member);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<List<Member>> create(List<Map<String,Object>> memberMap,
                                         Long idPrincipal, Long idCorporate,
                                         String actionUsername) {

        if(idPrincipal==null){
            return ResultFactory.getFailResult("Could not add members with an invalid Principal ID.");
        }

        if(idCorporate==null||idCorporate<1L){
            return ResultFactory.getFailResult("Could not add members with an invalid Scheme ID");
        }

        Member principal = memberRepo.findOne(idPrincipal);

        if(principal==null){
            return ResultFactory.getFailResult("Could not find Principal with ID ["+idPrincipal+"]. Member creation failed.");
        }

        Corporate corporate = corporateRepo.findOne(idCorporate);
        if(corporate==null){
            return ResultFactory.getFailResult("Could not find scheme with ID ["+idCorporate+"]. Member creation failed.");
        }

        List<Member> members = new ArrayList<>();

        StringBuilder errs = new StringBuilder();
        long cnt = memberRepo.countByPrincipal(principal);
        String[] rawPrefix = principal.getMemberNo().split("/");
        String prefix = rawPrefix[0].concat("/").concat(rawPrefix[1]);

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

            Member.MemberBuilder memberBuilder = new Member.MemberBuilder(firstName,surname,sex,dob,memberType,corporate)
                    .memberNo(memNum)
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
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Stream<Member>> create(List<Map<String, Object>> memberMap,
                                         String actionUsername) {

        return null;

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Member> update(Long idMember,
                                 String memberNo,
                                 String firstName,
                                 String surname,
                                 String otherNames,
                                 Sex sex,
                                 LocalDate dob,
                                 MemberType memberType,
                                 Long idCorporate,
                                 String actionUsername) {

        if(idMember==null || idMember<1L){
            return ResultFactory.getFailResult("Cannot update member with null ID.");
        }

        if(idCorporate==null||idCorporate<1L){
            return ResultFactory.getFailResult("Cannot update member with null scheme ID");
        }

        Optional<Member> memberFromIdOpt = memberRepo.getOne(idMember);

        if(!memberFromIdOpt.isPresent()){
            return ResultFactory.getFailResult("Member with ID ["+idMember+"] is not valid. Update failed.");
        }

        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate==null){
            return ResultFactory.getFailResult("Scheme with ID ["+idCorporate+"] is not valid. Update failed.");
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

        Member member = new Member.MemberBuilder(firstName,surname,sex,dob,memberType,corporate)
                .otherNames(otherNames).memberNo(memberNo).build();

        memberRepo.save(member);

        return ResultFactory.getSuccessResult(member);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Result<Page<Member>> findActive(int pageNum, int size,String actionUsername) {

        PageRequest pageRequest = new PageRequest(pageNum-1,size, Sort.Direction.ASC,"surname");

        Page<Member> memberPage = memberRepo.findActive(pageRequest);

        return ResultFactory.getSuccessResult(memberPage);

    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Result<Page<Member>> findAll(int pageNum, int size, String actionUsername) {

        PageRequest pageRequest = new PageRequest(pageNum-1,size, Sort.Direction.ASC,"surname");

        Page<Member> memberPage = memberRepo.findAll(pageRequest);

        return ResultFactory.getSuccessResult(memberPage);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Result<Page<Member>> findAllCovered(int pageNum, int size, String actionUsername) {

        PageRequest pageRequest = new PageRequest(pageNum-1,size, Sort.Direction.ASC,"surname");

        Page<Member> memberPage = memberRepo.findAllCovered(pageRequest);

        return ResultFactory.getSuccessResult(memberPage);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Result<List<Member>> findByPrincipal(Long idPrincipal, String actionUsername) {

        if(idPrincipal==null || idPrincipal<1){
            return ResultFactory.getFailResult("Invalid Principal ID ["+idPrincipal+"] supplied.");
        }

        Member principal = memberRepo.findOne(idPrincipal);

        if(principal==null){
            return ResultFactory.getFailResult("No principal with ID ["+idPrincipal+"] was found.");
        }

        List<Member> memberList = memberRepo.findByPrincipal(principal);

        if(memberList.isEmpty()){
            return ResultFactory.getFailResult("Principal ["+principal.getMemberNo()+"] has no dependants yet.");
        }

        return ResultFactory.getSuccessResult(memberList);

    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Result<Page<Member>> findByCorpAnniv(int pageNum, int size, Long idCorpAnniv, String actionUsername) {

        if(idCorpAnniv==null || idCorpAnniv<1){
            return ResultFactory.getFailResult("Invalid policy term provided.");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);

        if(corpAnniv==null){
            return ResultFactory.getFailResult("No policy term with ID ["+idCorpAnniv+"] was found.");
        }

        PageRequest pageRequest = new PageRequest(pageNum-1,size, Sort.Direction.ASC,"surname");

        Page<Member> memberPage = memberRepo.findByAnniversary(corpAnniv,pageRequest);

        if(memberPage.getNumberOfElements()<1){
            return ResultFactory.getFailResult("No members have been set up for this anniversary");
        }

        return ResultFactory.getSuccessResult(memberPage);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    public Result<List<Member>> findPrincipals(Long idCorporate, String actionUsername) {

        if(idCorporate==null || idCorporate<1){
            return ResultFactory.getFailResult("Invalid scheme supplied.");
        }

        Optional<Corporate> corporateOpt = corporateRepo.getOne(idCorporate);
        if(!corporateOpt.isPresent()){
            return ResultFactory.getFailResult("No scheme with ID ["+idCorporate+"] was found.");
        }

        List<Member> principals = memberRepo.findPrincipals(corporateOpt.get());

        return ResultFactory.getSuccessResult(principals);
    }

    private String generateMemberNo(Member principal){

        String rawPrefix = principal.getMemberNo();

        String[] numArray = rawPrefix.split("/");

        String prefix = numArray[0].concat("/").concat(numArray[1]);

        long newNum = memberRepo.countByPrincipal(principal)+1;
        String newNumStr = String.valueOf(newNum);
        int i = newNumStr.length();
        StringBuilder strBuilder = new StringBuilder(newNumStr);
        if (i<2){
            strBuilder.insert(0,"0");
        }

        return strBuilder.insert(0,"/").insert(0,prefix).toString();
    }

    private Map<String,String> generatePrincipalDtl(Corporate corporate){

        Map famMap = new HashMap();

        String prefix = corporate.getAbbreviation();

        long newNum = memberRepo.countByCorporate(corporate) + 1;
        String newNumStr = String.valueOf(newNum);
        int i = newNumStr.length();
        StringBuilder strBuilder = new StringBuilder(newNumStr);
        if (i<4){
            while(i<4) {
                strBuilder.insert(0,"0");
                i++;
            }
        }

        String famNo = strBuilder.insert(0,"/").insert(0,prefix).toString();
        famMap.put("famNum",famNo);
        famMap.put("memNum",famNo.concat("/00"));

        return famMap;
    }

}
