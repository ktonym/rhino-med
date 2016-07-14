package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.BenefitRef;
import ke.co.rhino.uw.service.IBenefitRefService;
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

/**
 * Created by akipkoech on 14/07/2016.
 */
@Controller
@RequestMapping("/uw/benefitref")
public class BenefitRefHandler extends AbstractHandler{


    @Autowired
    private IBenefitRefService benefitRefService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        //Integer intBen = Integer.valueOf(jsonObj.getString("benefitCode"));

        Result<BenefitRef> ar = benefitRefService.create(
                //intBen,
                jsonObj.getString("benefitName"),
                jsonObj.getString("description"),
                getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value="/update", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String update(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long benefitCode = ((JsonNumber) jsonObj.get("benefitCode")).longValue();

        Result<BenefitRef> ar = benefitRefService.update(
                benefitCode,
                jsonObj.getString("benefitName"),
                jsonObj.getString("description"),
                getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping (value = "/delete", method=RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String removeBenefit(
            @RequestParam(value="benefitCode", required = true) String benefitCodeStr,
            HttpServletRequest request){

        Long benefitCode = Long.parseLong(benefitCodeStr);
        Result<BenefitRef> ar = benefitRefService.remove(benefitCode,getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String searchBenefit(
            @RequestParam(value = "benefitName",required = true) String benefitName,
            HttpServletRequest request){

        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<BenefitRef>> ar = benefitRefService.search(pageNo,size,benefitName,getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAllBenefits(HttpServletRequest request){

        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<BenefitRef>> ar = benefitRefService.findAll(pageNo,size,getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findBenefit(
            @RequestParam(value = "benefitCode") String benefitCodeStr,
            HttpServletRequest request){

        Long benefitCode = Long.parseLong(benefitCodeStr);
        Result<BenefitRef> ar = benefitRefService.find(
                benefitCode,getUser());

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
