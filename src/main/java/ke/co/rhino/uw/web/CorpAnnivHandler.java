package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.Category;
import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.Intermediary;
import ke.co.rhino.uw.service.ICategoryService;
import ke.co.rhino.uw.service.ICorpAnnivService;
import ke.co.rhino.uw.service.ICorpService;
import ke.co.rhino.uw.service.IIntermediaryService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 03/02/2016.
 */
@Controller
@RequestMapping("/uw/corpanniv")
public class CorpAnnivHandler extends AbstractHandler {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ICorpAnnivService corpAnnivService;

    @Autowired
    private IIntermediaryService intermediaryService;

    @Autowired
    private ICategoryService categoryService;
    
    @Autowired
    private ICorpService corpService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAllAnnivs(

            @RequestParam(value = "idCorporate", required = true) String idCorpStr,

            HttpServletRequest request){

            Long idCorporate = Long.parseLong(idCorpStr);

            Result<List<CorpAnniv>> ar = corpAnnivService.findByCorporate(idCorporate, getUser());

            if (ar.isSuccess()) {
                JsonObjectBuilder builder = Json.createObjectBuilder();
                builder.add("success", true);
                JsonArrayBuilder annivsArrayBuilder = Json.createArrayBuilder();

                for (CorpAnniv anniv: ar.getData()) {

                    Intermediary intermediary = intermediaryService.findByAnniv(anniv.getId(),getUser()).getData();
                    JsonObjectBuilder intBuilder = Json.createObjectBuilder()
                            .add("idIntermediary", intermediary.getId())
                            .add("email", intermediary.getEmail() == null ? "" : intermediary.getEmail())
                            .add("name", intermediary.getName() == null ? "" : intermediary.getName())
                            .add("tel", intermediary.getTel() == null ? "" : intermediary.getTel())
                            .add("postalAddress", intermediary.getPostalAddress() == null ? "": intermediary.getPostalAddress() )
                            .add("town", intermediary.getTown() == null ? "" : intermediary.getTown() )
                            .add("pin", intermediary.getPin() == null ? "" : intermediary.getPin())
                            .add("street", intermediary.getStreet() == null ? "" : intermediary.getStreet() );

                    Corporate corp = corpService.find(idCorporate,getUser()).getData();
                    JsonObjectBuilder corpBuilder = Json.createObjectBuilder()
                            .add("idCorporate", corp.getId())
                            .add("pin", corp.getPin() == null ? "" : corp.getPin())
                            .add("name", corp.getName())
                            .add("email", corp.getEmail() == null ? "" : corp.getEmail())
                            .add("postalAddress", corp.getPostalAddress() == null ? "" : corp.getPostalAddress())
                            .add("tel", corp.getTel() == null ? "" : corp.getTel())
                            .add("lastUpdate", corp.getLastUpdate() == null ? "" : corp.getLastUpdate().format(DATE_FORMAT_yyyyMMddHHmm))
                            .add("abbreviation", corp.getAbbreviation() == null ? "" : corp.getAbbreviation());

                    Result<List<Category>> catAr = categoryService.findByAnniv(anniv.getId());
                    if(catAr.isSuccess()){
                        JsonArrayBuilder categoryBuilder = Json.createArrayBuilder();
                        for (Category cat:catAr.getData()) {
                            categoryBuilder.add(
                                    Json.createObjectBuilder()
                                        .add("idCategory", cat.getId())
                                        .add("cat", cat.getCat())
                                        .add("description", cat.getDescription())
                            );
                        }
                        annivsArrayBuilder.add(Json.createObjectBuilder().add("categories", categoryBuilder));
                    }


                    annivsArrayBuilder.add(
                            Json.createObjectBuilder()
                                    .add("intermediary",intBuilder)
                                    .add("corporate", corpBuilder)
                                    .add("idCorpAnniv", anniv.getId())
                                    .add("anniv", anniv.getAnniv())
                                    .add("inception", anniv.getInception() == null ? "" : anniv.getInception().format(DATE_FORMAT_yyyyMMdd))
                                    .add("expiry", anniv.getExpiry() == null ? "" :anniv.getExpiry().format(DATE_FORMAT_yyyyMMdd))
                                    .add("renewalDate", anniv.getRenewalDate() == null ? "" :anniv.getRenewalDate().format(DATE_FORMAT_yyyyMMdd))
                                    .add("lastUpdate", anniv.getLastUpdate() == null ? "" : anniv.getLastUpdate().format(DATE_FORMAT_yyyyMMddHHmm))
                    );
                }

                builder.add("data",annivsArrayBuilder);
                return toJsonString(builder.build());
            } else {
                return getJsonErrorMsg(ar.getMsg());
            }

    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String storeAnniv(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);

        String inceptionVal = jsonObj.getString("inception");
        String expiryVal = jsonObj.getString("expiry");
        String renewalDateVal = jsonObj.getString("renewalDate");

        Long idCorporate = ((JsonNumber) jsonObj.get("idCorporate")).longValue();
        Long idIntermediary = ((JsonNumber) jsonObj.get("intermediaryId")).longValue();
        Boolean isOpen = jsonObj.getBoolean("isOpen");

        Result<CorpAnniv> ar = corpAnnivService.create(
                idCorporate,
                idIntermediary,
                getIntegerValue(jsonObj.get("anniv")),
                LocalDate.parse(inceptionVal, DATE_FORMAT_yyyyMMdd),
                LocalDate.parse(expiryVal, DATE_FORMAT_yyyyMMdd),
                LocalDate.parse(renewalDateVal, DATE_FORMAT_yyyyMMdd),
                getUser()
        );

        if(ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value="/update", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String update(@RequestParam(value="data",required = true) String jsonData,
                         HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);

        String inceptionVal = jsonObj.getString("inception");
        String expiryVal = jsonObj.getString("expiry");
        String renewalDateVal = jsonObj.getString("renewalDate");

        Long idCorporate = ((JsonNumber) jsonObj.get("corporate.idCorporate")).longValue();
        Long idCorpAnniv = ((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
        Long idIntermediary = ((JsonNumber) jsonObj.get("idIntermediary")).longValue();

        Result<CorpAnniv> ar = corpAnnivService.update(
                idCorporate,
                idCorpAnniv,
                idIntermediary,
                getIntegerValue(jsonObj.get("anniv")),
                LocalDate.parse(inceptionVal, DATE_FORMAT_yyyyMMdd),
                LocalDate.parse(expiryVal, DATE_FORMAT_yyyyMMdd),
                LocalDate.parse(renewalDateVal, DATE_FORMAT_yyyyMMdd),
                getUser()
        );

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value="/delete", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String removeAnniv(
            @RequestParam(value = "idCorpAnniv", required = true) Long idCorpAnniv,
            HttpServletRequest request){

        Result<CorpAnniv> ar = corpAnnivService.remove(idCorpAnniv, getUser());

        if(ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    private String getUser(){
        return authenticationFacade.getAuthentication().getName();
    }

}
