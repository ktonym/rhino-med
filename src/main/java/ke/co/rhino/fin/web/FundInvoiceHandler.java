package ke.co.rhino.fin.web;

import ke.co.rhino.fin.entity.AdminFeeType;
import ke.co.rhino.fin.entity.FundInvoice;
import ke.co.rhino.fin.service.IFundInvoiceService;
import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.vo.Result;
import org.apache.el.lang.ELArithmetic;
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
import java.time.LocalDate;

/**
 * Created by akipkoech on 18/07/2016.
 */
@Controller
@RequestMapping("/fin/fundinvoice")
public class FundInvoiceHandler extends AbstractHandler {

    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private IFundInvoiceService service;

    @RequestMapping(value = "/create",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public String create(@RequestParam(value = "data")String jsonData,HttpServletRequest request){

        JsonObject jsonObject = parseJsonObject(jsonData);
        BigDecimal amount = ((JsonNumber) jsonObject.get("amount")).bigDecimalValue();
        String invoiceDateStr = jsonObject.getString("invoiceDate");
        LocalDate invoiceDate = LocalDate.parse(invoiceDateStr, DATE_FORMAT_yyyyMMdd);
        Long idCorpBenefit = ((JsonNumber) jsonObject.get("idCorpBenefit")).longValue();
        AdminFeeType adminFeeType = AdminFeeType.valueOf(jsonObject.getString("adminFeeType"));
        Double adminFeePercent = ((JsonNumber) jsonObject.get("adminFeePercent")).doubleValue();
        BigDecimal perVisitAmount = ((JsonNumber) jsonObject.get("perVisitAmount")).bigDecimalValue();
        BigDecimal flatRateAmount = ((JsonNumber) jsonObject.get("flatRateAmount")).bigDecimalValue();
        BigDecimal ratePerHead = ((JsonNumber) jsonObject.get("ratePerHead")).bigDecimalValue();

        Result<FundInvoice> ar = service.create(amount, invoiceDate,adminFeeType,adminFeePercent,perVisitAmount,flatRateAmount,ratePerHead, idCorpBenefit, getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public String update(@RequestParam(value = "data")String jsonData,HttpServletRequest request){

        JsonObject jsonObject = parseJsonObject(jsonData);

        Long idFundInvoice = ((JsonNumber) jsonObject.get("idFundInvoice")).longValue();
        String invoiceNumber = jsonObject.getString("invoiceNumber");
        BigDecimal amount = ((JsonNumber) jsonObject.get("amount")).bigDecimalValue();
        String invoiceDateStr = jsonObject.getString("invoiceDate");
        LocalDate invoiceDate = LocalDate.parse(invoiceDateStr, DATE_FORMAT_yyyyMMdd);
        Long idCorpBenefit = ((JsonNumber) jsonObject.get("idCorpBenefit")).longValue();
        AdminFeeType adminFeeType = AdminFeeType.valueOf(jsonObject.getString("adminFeeType"));
        Double adminFeePercent = ((JsonNumber) jsonObject.get("adminFeePercent")).doubleValue();
        BigDecimal perVisitAmount = ((JsonNumber) jsonObject.get("perVisitAmount")).bigDecimalValue();
        BigDecimal flatRateAmount = ((JsonNumber) jsonObject.get("flatRateAmount")).bigDecimalValue();
        BigDecimal ratePerHead = ((JsonNumber) jsonObject.get("ratePerHead")).bigDecimalValue();

        Result<FundInvoice> ar = service.update(idFundInvoice,invoiceNumber,
                amount,invoiceDate,adminFeeType,adminFeePercent,perVisitAmount,
                flatRateAmount,ratePerHead,idCorpBenefit,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/findAll",method = RequestMethod.GET,produces = {"application/json"})
    @ResponseBody
    public String findAll(@RequestParam(value = "idCorporate")String idCorporateStr,HttpServletRequest request){

        Long idCorporate = Long.valueOf(idCorporateStr);

        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        int size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<FundInvoice>> ar = service.findAll(pageNo,size,idCorporate,getUser());

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
