package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.Principal;
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

/**
 * Created by akipkoech on 16/05/2016.
 */
@Controller
@RequestMapping("/uw/principal")
public class PrincipalHandler extends AbstractHandler{

    @Autowired
    private IPrincipalService principalService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        String firstName = jsonObj.getString("firstName");
        String surname = jsonObj.getString("surname");
        String otherNames = jsonObj.getString("otherNames");
        String dobVal = jsonObj.getString("dob");
        LocalDate dob = LocalDate.parse(dobVal, DATE_FORMAT_yyyyMMdd);
        Long idCorporate = ((JsonNumber) jsonObj.get("idCorporate")).longValue();

        Result<Principal> ar = principalService.create(firstName,surname,otherNames,dob,idCorporate,getUser());

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
            HttpServletRequest request
    ) {

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idPrincipal = ((JsonNumber) jsonObj.get("idPrincipal")).longValue();
        String famNum = jsonObj.getString("familyNo");
        String firstName = jsonObj.getString("firstName");
        String surname = jsonObj.getString("surname");
        String otherNames = jsonObj.getString("otherNames");
        String dobVal = jsonObj.getString("dob");
        LocalDate dob = LocalDate.parse(dobVal, DATE_FORMAT_yyyyMMdd);
//        Long idCorporate = ((JsonNumber) jsonObj.get("idCorporate")).longValue();

        Result<Principal> ar = principalService.update(idPrincipal, famNum, firstName,
                surname, otherNames, dob, getUser());

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
        Long idPrincipal = Long.valueOf(jsonObj.getString("idPrincipal"));

        Result<Principal> ar = principalService.remove(idPrincipal, getUser());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(@RequestParam(value = "idCorporate", required = true) String idCorpStr,
            HttpServletRequest request) {

        logger.info("Showing page param: " + request.getParameter("page"));
        // The hack below was to sidestep null error when editing a corporate.
        // Yup, I copy-pasted this and adapted it for this controller method :-)
        // Sweet!!
        // Lazy... ;-)
        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Long idCorporate = Long.parseLong(idCorpStr);

        Result<Page<Principal>> ar = principalService.findByCorporate(idCorporate, pageNo, size, getUser());

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
