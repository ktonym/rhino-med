package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.Intermediary;
import ke.co.rhino.uw.entity.IntermediaryType;
import ke.co.rhino.uw.service.IIntermediaryService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * Created by akipkoech on 14/03/2016.
 */
@Controller
@RequestMapping("/uw/intermediary")
public class IntermediaryHandler extends AbstractHandler {

    //TODO complete find mappings

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private IIntermediaryService intermediaryService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(@RequestParam(value = "data", required = true) String jsonData, HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        String joinDateVal = jsonObj.getString("joined");
        String lastUpdateVal = jsonObj.getString("lastUpdate");

        Result<Intermediary> ar = intermediaryService.create(
                jsonObj.getString("name"),
                IntermediaryType.valueOf(jsonObj.getString("type")),
                jsonObj.getString("pin"),
                jsonObj.getString("email"),
                jsonObj.getString("tel"),
                jsonObj.getString("town"),
                jsonObj.getString("street"),
                jsonObj.getString("postalAddress"),
                LocalDate.parse(joinDateVal,DATE_FORMAT_yyyyMMdd),
                LocalDateTime.parse(lastUpdateVal,DATE_FORMAT_yyyyMMdd),
                getUser()
        );

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String update(@RequestParam(value = "data",required = true) String jsonData,
                         HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        IntermediaryType type = IntermediaryType.valueOf(jsonObj.getString("type"));
        String joinDateVal = jsonObj.getString("joined");
//        String lastUpdateVal = jsonObj.getString("lastUpdate");
        Long intId = ((JsonNumber) jsonObj.get("idIntermediary")).longValue();

        Result<Intermediary> ar = intermediaryService.update(intId,
                jsonObj.getString("name"),
                type, jsonObj.getString("pin"), jsonObj.getString("email"),
                jsonObj.getString("tel"), jsonObj.getString("town"), jsonObj.getString("street"), jsonObj.getString("postalAddress"),
                LocalDate.parse(joinDateVal, DATE_FORMAT_yyyyMMdd),
                LocalDateTime.now(),
                getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }


    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String delete(@RequestParam(value = "data",required = true) String jsonData,
                         HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long intId = ((JsonNumber) jsonObj.get("idIntermediary")).longValue();

        Result<Intermediary> ar = intermediaryService.remove(intId, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessMsg(ar.getMsg()); //trying out for first time!!
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(HttpServletRequest request) {

        //User sessionUser = getSessionUser(request);

        Result<List<Intermediary>> ar = intermediaryService.findAll(getUser());

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
