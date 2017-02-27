package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.BenefitStatus;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.service.ICorpMemberBenefitService;
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
import java.util.Optional;

/**
 * Created by akipkoech on 15/07/2016.
 */
@Controller
@RequestMapping("/uw/corpmemberbenefit")
public class CorpMemberBenefitHandler extends AbstractHandler {

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private ICorpMemberBenefitService service;

    @RequestMapping(value = "/create",method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request
    ){

        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idMember = ((JsonNumber) jsonObj.get("idMember")).longValue();
        Long idCorpAnniv = ((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
        Long idCorpBenefit = ((JsonNumber) jsonObj.get("idCorpBenefit")).longValue();
        Optional<Long> idParentCorpBenefitOpt = Optional.of(((JsonNumber) jsonObj.get("idParentCorpBenefit")).longValue());
        BenefitStatus status = BenefitStatus.valueOf(jsonObj.getString("status"));
        String wefStr = jsonObj.getString("wef");
        LocalDate wef = LocalDate.parse(wefStr,DATE_FORMAT_yyyyMMdd);

        Result<CorpMemberBenefit> ar = service.create(idMember,idCorpAnniv,idCorpBenefit,idParentCorpBenefitOpt,status,wef,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String update(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request
    ){
        // just realized there's completely no difference between create and update for this particular entity
        // so why not just use one method for both?
        // ;-)
        return create(jsonData,request);

//        JsonObject jsonObj = parseJsonObject(jsonString);
//
//        Long idMember = ((JsonNumber) jsonObj.get("idMember")).longValue();
//        Long idCorpAnniv = ((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
//        Long idCorpBenefit = ((JsonNumber) jsonObj.get("idCorpBenefit")).longValue();
//        Long idParentCorpBenefit = ((JsonNumber) jsonObj.get("idParentCorpBenefit")).longValue();
//        BenefitStatus status = BenefitStatus.valueOf(jsonObj.getString("status"));
//        String wefStr = jsonObj.getString("wef");
//        LocalDate wef = LocalDate.parse(wefStr,DATE_FORMAT_yyyyMMdd);
//
//        Result<CorpMemberBenefit> ar = service.update(idMember,idCorpAnniv,idCorpBenefit,idParentCorpBenefit,status,wef,getUser());
//        if(ar.isSuccess()){
//            return getJsonSuccessData(ar.getData());
//        } else {
//            return getJsonErrorMsg(ar.getMsg());
//        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(@RequestParam(value = "data", required = true) String jsonData,
                          HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idCorpAnniv = ((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
        Long idMember = ((JsonNumber) jsonObj.get("idMember")).longValue();

        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));
        Result<Page<CorpMemberBenefit>> ar = service.findAll(idMember,idCorpAnniv, pageNo, size, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String delete(@RequestParam(value = "data") String jsonData, HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idMember = ((JsonNumber) jsonObj.get("idMember")).longValue();
        Long idCorpAnniv = ((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
        Long idCorpBenefit = ((JsonNumber) jsonObj.get("idCorpBenefit")).longValue();

        Result<CorpMemberBenefit> ar = service.delete(idMember,idCorpAnniv,idCorpBenefit,getUser());

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
