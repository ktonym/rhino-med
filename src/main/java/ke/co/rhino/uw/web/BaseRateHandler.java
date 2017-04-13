package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.StdPremiumRate;
import ke.co.rhino.uw.service.IStdPremiumRateService;
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
 * Created by user on 12/04/2017.
 */
@Controller
@RequestMapping("/uw/baserate")
public class BaseRateHandler extends  AbstractHandler{

    @Autowired
    private IStdPremiumRateService service;
    @Autowired
    private IAuthenticationFacade facade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String create(@RequestParam(value = "data") String jsonData){

        JsonObject jsonObject = parseJsonObject(jsonData);

        BigDecimal upperLimit = getBigDecimalValue(jsonObject.get("upperLimit"));
        BigDecimal premium = getBigDecimalValue(jsonObject.get("premium"));
        String familySize = jsonObject.getString("familySize");
        Long benefitCode = ((JsonNumber) jsonObject.get("benefitCode")).longValue();

        Result<StdPremiumRate> ar = service.create(upperLimit,premium,familySize,benefitCode,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String update(@RequestParam(value = "data") String jsonData){

        JsonObject jsonObject = parseJsonObject(jsonData);

        Long idPremiumRate = ((JsonNumber) jsonObject.get("idPremiumRate")).longValue();
        BigDecimal upperLimit = getBigDecimalValue(jsonObject.get("upperLimit"));
        BigDecimal premium = getBigDecimalValue(jsonObject.get("premium"));
        String familySize = jsonObject.getString("familySize");
        Long benefitCode = ((JsonNumber) jsonObject.get("benefitCode")).longValue();

        Result<StdPremiumRate> ar = service.update(idPremiumRate,upperLimit,premium,familySize,benefitCode,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String findAll(HttpServletRequest request){

        int page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<StdPremiumRate>> ar = service.findAll(page,size,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    private String getUser(){
        return facade.getAuthentication().getName();
    }

}
