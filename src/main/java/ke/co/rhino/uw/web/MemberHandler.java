package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberType;
import ke.co.rhino.uw.entity.Sex;
import ke.co.rhino.uw.service.IMemberService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Created by akipkoech on 11/07/2016.
 */
@Controller
@RequestMapping("/uw/member")
public class MemberHandler extends AbstractHandler{

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);
        String firstName = jsonObj.getString("firstName");
        String surname = jsonObj.getString("surname");
        String otherNames = jsonObj.getString("otherNames");
        Sex sex = Sex.valueOf(jsonObj.getString("sex"));
        MemberType memberType = MemberType.valueOf(jsonObj.getString("memberType"));
        String dobVal = jsonObj.getString("dob");
        LocalDate dob = LocalDate.parse(dobVal, DATE_FORMAT_yyyyMMdd);
        Optional<Long> idPrincipalOpt;
        try {
            //Optional<JsonObject> principalOpt = Optional.of( parseJsonObject(jsonObj.get("principal").toString()));
            idPrincipalOpt = Optional.of( ((JsonNumber) jsonObj.get("principal.idPrincipal")).longValue());
        } catch (NullPointerException npe){
            idPrincipalOpt = Optional.empty();
        }
        /*if(jsonObj.get("principal").getValueType()==null){
            idPrincipalOpt = Optional.of(null);
        } else {
            Optional<JsonObject> principalOpt = Optional.of( parseJsonObject(jsonObj.get("principal").toString()));
            idPrincipalOpt = Optional.of( ((JsonNumber) principalOpt.get().get("idPrincipal")).longValue());
        }*/

        Long idCorporate = ((JsonNumber) jsonObj.get("idCorporate")).longValue();

        Result<Member> ar = memberService.create(idCorporate,idPrincipalOpt,firstName,surname,otherNames,sex,dob,memberType,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/update", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public String update(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idMember = ((JsonNumber) jsonObj.get("idMember")).longValue();
        String memberNo = jsonObj.getString("memberNo");
        String firstName = jsonObj.getString("firstName");
        String surname = jsonObj.getString("surname");
        String otherNames = jsonObj.getString("otherNames");
        Sex sex = Sex.valueOf(jsonObj.getString("sex"));
        MemberType memberType = MemberType.valueOf(jsonObj.getString("memberType"));
        String dobVal = jsonObj.getString("dob");
        LocalDate dob = LocalDate.parse(dobVal, DATE_FORMAT_yyyyMMdd);
        Long idCorporate = ((JsonNumber) jsonObj.get("idCorporate")).longValue();
        //Long idPrincipal = ((JsonNumber) jsonObj.get("idPrincipal")).longValue();

        Result<Member> ar = memberService.update(idMember, memberNo, firstName, surname, otherNames, sex, dob, memberType, idCorporate, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String delete(@RequestParam(value = "data", required = true) String jsonData,
                         HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idMember = Long.valueOf(jsonObj.getString("idMember"));

        Result<Member> ar = memberService.remove(idMember, getUser());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(@RequestParam(value = "idPrincipal", required = true) String idPrincipalStr,
                          HttpServletRequest request) {

        logger.info("Showing page param: " + request.getParameter("page"));
        /** The hack below was to sidestep null error when editing a corporate.
         *  Yup, I copy-pasted this and adapted it for this controller method :-)
         *  Sweet!!
         *  Lazy... ;-)
         *  int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
         *  Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));
         **/

        Long idPrincipal = Long.parseLong(idPrincipalStr);

        Result<List<Member>> ar = memberService.findByPrincipal(idPrincipal,getUser());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findByAnniv", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findByAnniv(@RequestParam(value = "idCorpAnniv", required = true) String idCorpAnnivStr, HttpServletRequest request){

        Long idCorpAnniv = Long.parseLong(idCorpAnnivStr);

        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<Member>> ar = memberService.findByCorpAnniv(pageNo, size, idCorpAnniv, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    private String getUser(){
        return authenticationFacade.getAuthentication().getName();
    }

    @RequestMapping(value="/principals", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getPrincipals(@RequestParam(value = "idCorporate") String corpIdStr){
        Long idCorporate = Long.valueOf(corpIdStr);
        Result<List<Member>> ar = memberService.findPrincipals(idCorporate,getUser());
        if(ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String memberTree(@RequestParam(value = "idCorporate") String corpIdStr){
        Long idCorporate = Long.valueOf(corpIdStr);

        // First find principals then iterate through them, picking any dependants along the way
        Result<List<Member>> ar = memberService.findPrincipals(idCorporate,getUser());

        if(ar.isSuccess()) {

            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("success", true);
            JsonArrayBuilder principalArrBuilder = Json.createArrayBuilder();

            for (Member principal: ar.getData()){
                List<Member> dependants = memberService.findByPrincipal(principal.getId(),getUser()).getData();
                JsonArrayBuilder dependantArrBuilder = Json.createArrayBuilder();
                //logger.info("Checking out dependants variable.");
               // System.out.print(dependants.size());
                if(dependants!=null && dependants.size()>0){
                    for (Member dependant: dependants){
                        dependantArrBuilder.add(
                                Json.createObjectBuilder()
                                .add("idMember", dependant.getId())
                                .add("memberNo", dependant.getMemberNo())
                                .add("sex", dependant.getSex().toString())
                                .add("firstName", dependant.getFirstName())
                                .add("surname", dependant.getSurname())
                                .add("otherNames", dependant.getSurname()==null?"":dependant.getSurname())
                                .add("memberType", dependant.getMemberType().toString())
                                .add("dob", dependant.getDob()==null?"":DATE_FORMAT_yyyyMMdd.format(dependant.getDob()))
                                .add("fullName",
                                        dependant.getFirstName().concat(" ")
                                                .concat(dependant.getSurname()==null?"":dependant.getSurname()).concat(" ")
                                                .concat(dependant.getOtherNames()==null?"":dependant.getOtherNames()))
                                .add("idPrincipal", dependant.getPrincipal().getId())

                        );
                    }
                }

                principalArrBuilder.add(
                        Json.createObjectBuilder()
                            .add("dependants", dependantArrBuilder)
                            .add("idCorporate", idCorporate)
                            .add("idMember", principal.getId())
                            .add("memberNo", principal.getMemberNo())
                            .add("sex", principal.getSex().toString())
                            .add("firstName", principal.getFirstName())
                            .add("surname", principal.getSurname())
                            .add("otherNames", principal.getSurname()==null?"":principal.getSurname())
                            .add("memberType", principal.getMemberType().toString())
                            .add("dob", principal.getDob()==null?"":DATE_FORMAT_yyyyMMdd.format(principal.getDob()))
                            .add("fullName",
                                    principal.getFirstName().concat(" ")
                                            .concat(principal.getSurname()==null?"":principal.getSurname()).concat(" ")
                                            .concat(principal.getOtherNames()==null?"":principal.getOtherNames()))
                );

            }

            builder.add("members", principalArrBuilder);

            return toJsonString(builder.build());

        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

}
