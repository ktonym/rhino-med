package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberType;
import ke.co.rhino.uw.entity.Sex;
import ke.co.rhino.uw.service.IMemberService;
import ke.co.rhino.uw.service.IPrincipalService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

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
        Long idPrincipal = ((JsonNumber) jsonObj.get("idPrincipal")).longValue();

        Result<Member> ar = memberService.create(idPrincipal,firstName,surname,otherNames,sex,dob,memberType,getUser());
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
        Long idPrincipal = ((JsonNumber) jsonObj.get("idPrincipal")).longValue();

        Result<Member> ar = memberService.update(idMember, memberNo, firstName, surname, otherNames, sex, dob, memberType, getUser());

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
        // The hack below was to sidestep null error when editing a corporate.
        // Yup, I copy-pasted this and adapted it for this controller method :-)
        // Sweet!!
        // Lazy... ;-)
//        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
//        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Long idPrincipal = Long.parseLong(idPrincipalStr);

        Result<List<Member>> ar = memberService.findByPrincipal(idPrincipal,getUser());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }


    private String getUser(){
        return authenticationFacade.getAuthentication().getName();
    }

}
