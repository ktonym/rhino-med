package ke.co.rhino.fin.web;

import ke.co.rhino.fin.entity.AdminFee;
import ke.co.rhino.fin.service.IAdminFeeService;
import ke.co.rhino.security.IAuthenticationFacade;
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
 * Created by akipkoech on 20/07/2016.
 */
@Controller
@RequestMapping("/fin/adminfee")
public class AdminFeeHandler extends AbstractHandler{

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private IAdminFeeService service;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(@RequestParam(value = "data")String jsonData, HttpServletRequest request){

        JsonObject jsonObject = parseJsonObject(jsonData);
        Long idFundInvoice = ((JsonNumber) jsonObject.get("idFundInvoice")).longValue();
        BigDecimal amount = ((JsonNumber) jsonObject.get("amount")).bigDecimalValue();
        String notes = jsonObject.getString("notes");

        Result<AdminFee> ar = service.create(idFundInvoice, amount, notes, getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value="/findAll",method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(@RequestParam(value = "data")String jsonData,HttpServletRequest request){

        JsonObject jsonObject = parseJsonObject(jsonData);
        Long idCorpBenefit = ((JsonNumber) jsonObject.get("idCorpBenefit")).longValue();
        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        int size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));
        Result<Page<AdminFee>> ar = service.findAll(idCorpBenefit, pageNo, size, getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public String update(@RequestParam(value = "data")String jsonData, HttpServletRequest request){
        return getJsonErrorMsg("Under construction. Coming soon!");
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public String delete(@RequestParam(value = "data")String jsonData, HttpServletRequest request){
        return getJsonErrorMsg("Under construction. Coming soon!");
    }

    public String getUser(){
        return authenticationFacade.getAuthentication().getName();
    }

}
