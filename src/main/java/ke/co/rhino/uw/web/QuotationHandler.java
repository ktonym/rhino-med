package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.Quotation;
import ke.co.rhino.uw.service.IQuotationService;
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
 * Created by user on 31/03/2017.
 */
@Controller
@RequestMapping("/uw/quotation")
public class QuotationHandler extends AbstractHandler{

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private IQuotationService service;

    @RequestMapping(value = "/create",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String create(@RequestParam(value = "data") String jsonData/*, HttpServletRequest request*/){

        JsonObject jsonObject = parseJsonObject(jsonData);
        String quoteDateVal = jsonObject.getString("quotationDate");
        LocalDate quotationDate = LocalDate.parse(quoteDateVal,DATE_FORMAT_yyyyMMdd);
        Long idCorporate = ((JsonNumber) jsonObject.get("idCorporate")).longValue();

        Result<Quotation> ar = service.create(quotationDate,idCorporate,getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findByCorporate", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public String findByCorporate(@RequestParam(value = "idCorporate") String idCorpStr){

        Long idCorporate = Long.valueOf(idCorpStr);
        Result<List<Quotation>> ar = service.findByCorporate(idCorporate,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }


    @RequestMapping(value = "/findByDate", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String findByDate(@RequestParam(value = "date") String dateStr, HttpServletRequest request){

        LocalDate date = LocalDate.parse(dateStr,DATE_FORMAT_yyyyMMdd);
        int page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        int size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<Quotation>> ar = service.findByDate(date,page,size,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/findByDateBetween", method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public String findByDateBetween(@RequestParam(value = "fromDate") String fromDateStr,
                                    @RequestParam(value = "toDate") String toDateStr,
                                    HttpServletRequest request){

        LocalDate from = LocalDate.parse(fromDateStr,DATE_FORMAT_yyyyMMdd);
        LocalDate to = LocalDate.parse(toDateStr,DATE_FORMAT_yyyyMMdd);
        int page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        int size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<Quotation>> ar = service.findByDateBetween(from,to,page,size,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String delete(@RequestParam(value = "data", required = true) String jsonData){

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idQuotation = Long.valueOf(jsonObj.getString("idQuotation"));
        Result<Quotation> ar = service.delete(idQuotation,getUser());
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
