package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.MemberAnniversary;
import ke.co.rhino.uw.service.IMemberAnniversaryService;
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
 * Created by akipkoech on 12/07/2016.
 */
@Controller
@RequestMapping("/uw/memberanniv")
public class MemberAnnivHandler extends AbstractHandler{

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private IMemberAnniversaryService memberAnniversaryService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request)
    {
        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idMember = ((JsonNumber) jsonObj.get("idMember")).longValue();
        Long idCorpAnniv =((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
        String inceptionStr = jsonObj.getString("inception");
        LocalDate inception = LocalDate.parse(inceptionStr, DATE_FORMAT_yyyyMMdd);
        String expiryStr = jsonObj.getString("expiry");
        LocalDate expiry = LocalDate.parse(expiryStr, DATE_FORMAT_yyyyMMdd);

        Result<MemberAnniversary> ar = memberAnniversaryService.create(idMember, idCorpAnniv, inception, expiry, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String update(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request)
    {
        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idMember = ((JsonNumber) jsonObj.get("idMember")).longValue();
        Long idCorpAnniv =((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
        String inceptionStr = jsonObj.getString("inception");
        LocalDate inception = LocalDate.parse(inceptionStr, DATE_FORMAT_yyyyMMdd);
        String expiryStr = jsonObj.getString("expiry");
        LocalDate expiry = LocalDate.parse(expiryStr, DATE_FORMAT_yyyyMMdd);

        Result<MemberAnniversary> ar = memberAnniversaryService.update(idMember, idCorpAnniv, inception, expiry, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(
            @RequestParam(value = "idCorpAnniv",required = true) String idCorpAnnivStr,
            HttpServletRequest request)
    {
        Long idCorpAnniv = Long.parseLong(idCorpAnnivStr);

        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        int size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<MemberAnniversary>> ar = memberAnniversaryService.findAll(pageNo,size,idCorpAnniv,getUser());

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
