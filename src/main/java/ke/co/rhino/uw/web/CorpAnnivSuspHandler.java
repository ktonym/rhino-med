package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.CorpAnnivSuspension;
import ke.co.rhino.uw.service.ICorpAnnivSuspService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 03/02/2016.
 */
@Controller
@RequestMapping("/uw/corpannivsusp")
public class CorpAnnivSuspHandler extends AbstractHandler {

    @Autowired
    private ICorpAnnivSuspService corpAnnivSuspService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/store", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String storeSuspension(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request){

        //User sessionUser = getSessionUser(request);
        JsonObject jsonObj = parseJsonObject(jsonData);
        String startDateVal = jsonObj.getString("startDate");
        String endDateVal = jsonObj.getString("endDate");
        Long idCorpAnniv = Long.valueOf(jsonObj.getString("idCorpAnniv"));
        Result<CorpAnnivSuspension> ar = corpAnnivSuspService.store(
                getIntegerValue(jsonObj.get("idAnnivSusp")),
                idCorpAnniv,
                LocalDate.parse(startDateVal, DATE_FORMAT_yyyyMMdd),
                LocalDate.parse(endDateVal, DATE_FORMAT_yyyyMMdd),
                jsonObj.getString("reason"),
                getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAllSuspensions(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request){

        //User sessionUser = getSessionUser(request);
        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idCorpAnniv = Long.valueOf(jsonObj.getString("idCorpAnniv"));
        Result<List<CorpAnnivSuspension>> ar = corpAnnivSuspService.findAllInAnniv(
                idCorpAnniv,
                getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    private String getUser(){
        return authenticationFacade.getAuthentication().getName();
    }

}
