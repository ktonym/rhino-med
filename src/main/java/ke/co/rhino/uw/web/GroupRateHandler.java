package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.GroupRate;
import ke.co.rhino.uw.service.IGroupRateService;
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
import java.math.BigDecimal;

/**
 * Created by akipkoech on 15/07/2016.
 */
@Controller
@RequestMapping("/uw/grouprate")
public class GroupRateHandler extends AbstractHandler {

    @Autowired
    private IGroupRateService service;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(@RequestParam(value = "data", required = true) String jsonData,
                         HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idCorporate = ((JsonNumber)jsonObj.get("idCorporate")).longValue();
        Long benefitCode = ((JsonNumber)jsonObj.get("benefitCode")).longValue();
        String famSize = jsonObj.getString("familySize");
        BigDecimal upperLimit = ((JsonNumber)jsonObj.get("upperLimit")).bigDecimalValue();
        BigDecimal premium = ((JsonNumber)jsonObj.get("premium")).bigDecimalValue();

        Result<GroupRate> ar = service.create(idCorporate, benefitCode, famSize, upperLimit, premium, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }


    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String update(@RequestParam(value = "data", required = true) String jsonData,
                         HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idPremiumRate = ((JsonNumber)jsonObj.get("idPremiumRate")).longValue();
        Long idCorporate = ((JsonNumber)jsonObj.get("idCorporate")).longValue();
        Long benefitCode = ((JsonNumber)jsonObj.get("benefitCode")).longValue();
        String famSize = jsonObj.getString("familySize");
        BigDecimal upperLimit = ((JsonNumber)jsonObj.get("upperLimit")).bigDecimalValue();
        BigDecimal premium = ((JsonNumber)jsonObj.get("premium")).bigDecimalValue();

        Result<GroupRate> ar = service.update(idPremiumRate,
                                                idCorporate,
                                                benefitCode,
                                                famSize,
                                                upperLimit,
                                                premium, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String delete(@RequestParam(value = "data", required = true) String jsonData,
                         HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idPremiumRate = Long.getLong(jsonObj.getString("idPremiumRate"));

        Result<GroupRate> ar = service.remove(idPremiumRate, getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(HttpServletRequest request){

        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        int size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<GroupRate>> ar = service.findAll(pageNo, size, getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/findByCorporate", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findByCorporate(@RequestParam(value = "data") String jsonData, HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idCorporate = Long.getLong(jsonObj.getString("idCorporate"));
        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        int size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<GroupRate>> ar = service.findByCorporate(idCorporate,pageNo,size,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }


    public String getUser(){
        return authenticationFacade.getAuthentication().getName();
    }

}
