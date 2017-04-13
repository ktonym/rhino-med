package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.Quotation;
import ke.co.rhino.uw.entity.QuotationItem;
import ke.co.rhino.uw.service.QuotationItemService;
import ke.co.rhino.uw.service.QuotationService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by user on 03/04/2017.
 */
@Controller
@RequestMapping("/uw/quotationitem")
public class QuotationItemHandler extends AbstractHandler{

    @Autowired
    private QuotationItemService service;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private QuotationService quotationService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String create(@RequestParam(value = "data") String jsonData){

        JsonObject jsonObject = parseJsonObject(jsonData);

        Long idQuotation = ((JsonNumber) jsonObject.get("idQuotation")).longValue();
        Long idPremiumRate = ((JsonNumber) jsonObject.get("idPremiumRate")).longValue();
        String quoteDateStr = jsonObject.getString("quotationDate");
        LocalDate quotationDate = LocalDate.parse(quoteDateStr,DATE_FORMAT_yyyyMMdd);
        Long discountLoadFactor = ((JsonNumber) jsonObject.get("discountLoadFactor")).longValue();
        Integer quantity =  jsonObject.getInt("quantity");

        Result<QuotationItem> ar = service.create(idQuotation,idPremiumRate,quotationDate,discountLoadFactor,quantity,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String delete(@RequestParam(value = "data") String jsonData){

        JsonObject jsonObject = parseJsonObject(jsonData);

        Long idQuoteItem = ((JsonNumber) jsonObject.get("idQuoteItem")).longValue();
        Result<QuotationItem> ar = service.delete(idQuoteItem,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }


    @RequestMapping(value = "/findAll", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public String findAll(@RequestParam(value = "idQuotation") String idQuotationStr){

        Long idQuotation = Long.valueOf(idQuotationStr);
        Result<Quotation> ar = quotationService.findOne(idQuotation,getUser());
        if(ar.isSuccess()){
            JsonObjectBuilder builder = Json.createObjectBuilder();
            Quotation quote = ar.getData();
            builder.add("success",true)
                    .add("idQuotation",quote.getId())
                    .add("quotationDate", quote.getQuotationDate() == null ? "" : DATE_FORMAT_yyyyMMdd.format(quote.getQuotationDate()));

            Result<List<QuotationItem>> arItems = service.findAll(idQuotation,getUser());
            if(arItems.isSuccess()){
                List<QuotationItem> itemList = arItems.getData();
                JsonArrayBuilder itemsBuilder = Json.createArrayBuilder();
                BigDecimal sum = new BigDecimal(0);
                for (QuotationItem qi: itemList){
                    Integer qty = qi.getQuantity();
                    BigDecimal unit = qi.getPremiumRate().getPremium();
                    Long factor = qi.getLoadDiscountFactor();
                    BigDecimal total = unit.multiply(BigDecimal.valueOf(qty)).multiply(BigDecimal.valueOf(factor));
                    itemsBuilder.add(
                            Json.createObjectBuilder()
                                .add("idQuoteItem",qi.getId())
                                .add("quantity",qi.getQuantity())
                                .add("quoteItemDate",DATE_FORMAT_yyyyMMdd.format(qi.getQuoteItemDate()))
                                .add("familySize",qi.getPremiumRate().getFamilySize())
                                .add("limit",qi.getPremiumRate().getUpperLimit())
                                .add("unitPrice", qi.getPremiumRate().getPremium())
                                .add("loadDiscountFactor",qi.getLoadDiscountFactor())
                                .add("total",total.setScale(2,BigDecimal.ROUND_HALF_UP))
                    );
                    sum = sum.add(total);
                    sum.setScale(2,BigDecimal.ROUND_HALF_UP);
                }

                builder.add("items", itemsBuilder);
                builder.add("sum", sum);
            }
            return toJsonString(builder.build());
        }else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    private String getUser(){
        return authenticationFacade.getAuthentication().getName();
    }
}
