package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.service.ICorpAnnivService;
import ke.co.rhino.uw.service.ICorpService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 03/02/2016.
 */
@Controller
@RequestMapping("/uw/corpanniv")
public class CorpAnnivHandler extends AbstractHandler {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ICorpAnnivService corpAnnivService;
    
    @Autowired
    private ICorpService corpService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAllAnnivs(

            @RequestParam(value = "idCorporate", required = true) String idCorpStr,

            HttpServletRequest request){

            Long idCorporate = Long.parseLong(idCorpStr);

            Result<List<CorpAnniv>> ar = corpAnnivService.findByCorporate(idCorporate, getUser());

            if (ar.isSuccess()) {
                return getJsonSuccessData(ar.getData());
            } else {
                return getJsonErrorMsg(ar.getMsg());
            }

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String storeAnniv(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);

        String inceptionVal = jsonObj.getString("inception");
        String expiryVal = jsonObj.getString("expiry");
        String renewalDateVal = jsonObj.getString("renewalDate");

        Long idCorporate = ((JsonNumber) jsonObj.get("idCorporate")).longValue();
        Long idIntermediary = ((JsonNumber) jsonObj.get("idIntermediary")).longValue();

        Result<CorpAnniv> ar = corpAnnivService.create(
                idCorporate,
                idIntermediary,
                getIntegerValue(jsonObj.get("anniv")),
                LocalDate.parse(inceptionVal, DATE_FORMAT_yyyyMMdd),
                LocalDate.parse(expiryVal, DATE_FORMAT_yyyyMMdd),
                LocalDate.parse(renewalDateVal, DATE_FORMAT_yyyyMMdd),
                getUser()
        );

        if(ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value="/update", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String update(@RequestParam(value="data",required = true) String jsonData,
                         HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);

        String inceptionVal = jsonObj.getString("inception");
        String expiryVal = jsonObj.getString("expiry");
        String renewalDateVal = jsonObj.getString("renewalDate");

        Long idCorporate = ((JsonNumber) jsonObj.get("idCorporate")).longValue();
        Long idCorpAnniv = ((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
        Long idIntermediary = ((JsonNumber) jsonObj.get("idIntermediary")).longValue();

        Result<CorpAnniv> ar = corpAnnivService.update(
                idCorporate,
                idCorpAnniv,
                idIntermediary,
                getIntegerValue(jsonObj.get("anniv")),
                LocalDate.parse(inceptionVal, DATE_FORMAT_yyyyMMdd),
                LocalDate.parse(expiryVal, DATE_FORMAT_yyyyMMdd),
                LocalDate.parse(renewalDateVal, DATE_FORMAT_yyyyMMdd),
                getUser()
        );

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value="/delete", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String removeAnniv(
            @RequestParam(value = "idCorpAnniv", required = true) Long idCorpAnniv,
            HttpServletRequest request){

        Result<CorpAnniv> ar = corpAnnivService.remove(idCorpAnniv, getUser());

        if(ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    private String getUser(){
        return authenticationFacade.getAuthentication().getName();
    }

}
