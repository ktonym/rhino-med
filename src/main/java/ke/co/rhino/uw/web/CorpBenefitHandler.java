package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.BenefitType;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.MemberType;
import ke.co.rhino.uw.service.ICorpBenefitService;
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
import java.util.Optional;

/**
 * Created by akipkoech on 12/07/2016.
 */
@Controller
@RequestMapping("/uw/corpbenefit")
public class CorpBenefitHandler extends AbstractHandler{

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private ICorpBenefitService corpBenefitService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(
            @RequestParam(value = "data",required = true) String jsonData,
            HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);

        Long benefitCode = ((JsonNumber) jsonObj.get("benefitCode")).longValue();
        BigDecimal upperLimit = ((JsonNumber) jsonObj.get("upperLimit")).bigDecimalValue();
        MemberType memberType = MemberType.valueOf(jsonObj.getString("memberType"));
        BenefitType benefitType = BenefitType.valueOf(jsonObj.getString("benefitType"));
        boolean sharing = jsonObj.getString("sharing").equalsIgnoreCase("Y");
        boolean needPreAuth = jsonObj.getString("needPreAuth").equalsIgnoreCase("Y");
        Integer waitingPeriod = jsonObj.getInt("waitingPeriod");
        Optional<Long> idParentBenefitOpt = Optional.of(((JsonNumber) jsonObj.get("idParentBenefit")).longValue());
        Long idCategory = ((JsonNumber) jsonObj.get("idCategory")).longValue();


        Result<CorpBenefit> ar = corpBenefitService.create(benefitCode, upperLimit, memberType, benefitType, sharing, needPreAuth, waitingPeriod, idParentBenefitOpt, idCategory, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String update(
            @RequestParam(value = "data",required = true) String jsonData,
            HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idCorpBenefit = ((JsonNumber) jsonObj.get("idCorpBenefit")).longValue();
        Long benefitCode = ((JsonNumber) jsonObj.get("benefitCode")).longValue();
        BigDecimal upperLimit = ((JsonNumber) jsonObj.get("upperLimit")).bigDecimalValue();
        MemberType memberType = MemberType.valueOf(jsonObj.getString("memberType"));
        BenefitType benefitType = BenefitType.valueOf(jsonObj.getString("benefitType"));
        boolean sharing = jsonObj.getString("sharing").equalsIgnoreCase("Y");
        boolean needPreAuth = jsonObj.getString("needPreAuth").equalsIgnoreCase("Y");
        Integer waitingPeriod = jsonObj.getInt("waitingPeriod");
        Optional<Long> idParentBenefitOpt = Optional.of(((JsonNumber) jsonObj.get("idParentBenefit")).longValue());
        Long idCategory = ((JsonNumber) jsonObj.get("idCategory")).longValue();


        Result<CorpBenefit> ar = corpBenefitService.update(idCorpBenefit, benefitCode, upperLimit, memberType, benefitType, sharing, needPreAuth, waitingPeriod, idParentBenefitOpt, idCategory, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value="/delete",method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String delete(@RequestParam(value = "data", required = true) String jsonData,
                         HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idCorpBenefit = Long.valueOf(jsonObj.getString("idCorpBenefit"));

        Result<CorpBenefit> ar = corpBenefitService.remove(idCorpBenefit, getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(@RequestParam(value = "idCorpAnniv", required = true) String idCorpAnnivStr,
                          HttpServletRequest request) {

        Long idCorpAnniv = Long.parseLong(idCorpAnnivStr);

        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));
        Result<Page<CorpBenefit>> ar = corpBenefitService.findByCorpAnniv(idCorpAnniv,pageNo,size,getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findByCategory", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findByCategory(@RequestParam(value = "idCategory", required = true) String idCategoryStr,
                          HttpServletRequest request) {

        Long idCategory = Long.parseLong(idCategoryStr);

        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));
        Result<Page<CorpBenefit>> ar = corpBenefitService.findByCategory(idCategory, pageNo, size, getUser());

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
