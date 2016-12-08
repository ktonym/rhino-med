package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.service.ICorpService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 27/01/2016.
 */
@Controller
@RequestMapping("/uw/corporate")
public class CorpHandler extends AbstractHandler {

    @Autowired
    private ICorpService corpService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/create", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public String create(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request
    ) {

        //JsonArray jsonArray = parseJsonArray(jsonData);

         try {
             JsonObject jsonObj = parseJsonObject(jsonData);

             String joinDateVal = jsonObj.getString("joined");
            // String lastUpdateVal = jsonObj.getString("lastUpdate"); TODO figure out how to cast this to string when empty
             LocalDateTime lastUpdateVal = LocalDateTime.now();
             //Integer idCorp = Integer.valueOf(jsonObj.getString("idCorporate"));
             Result<Corporate> ar = corpService.create(
                     jsonObj.getString("name"),
                     jsonObj.getString("abbreviation"),
                     jsonObj.getString("pin"),
                     jsonObj.getString("tel"),
                     jsonObj.getString("email"),
                     jsonObj.getString("postalAddress"),
                     LocalDate.parse(joinDateVal, DATE_FORMAT_yyyyMMdd),
                     lastUpdateVal,
                     getUser());

             if (ar.isSuccess()) {
                 return getJsonSuccessData(ar.getData());
             } else {
                 return getJsonErrorMsg(ar.getMsg());
             }
        }catch(JsonException e){
            JsonArray jsonArray = parseJsonArray(jsonData);
             List<Map<String,Object>> mapList = new ArrayList<>();

             for ( int i = 0; i < jsonArray.size(); i++){

                 JsonObject jsonObj = jsonArray.getJsonObject(i);
                 Map map = new HashMap<>();

                 //Integer idCorpStr = jsonObj.getInt("idCorporate");
                 //long temp = (long) jsonObj.get("idCorporate");
//                 Integer idCorpInt = ((JsonNumber) jsonObj.get("idCorporate")).intValue();
//                 map.put("idCorporate", idCorpInt);//jsonObj.getInt("idCorporate"));
                 map.put("name", jsonObj.getString("name"));
                 map.put("abbreviation", jsonObj.getString("abbreviation"));
                 map.put("pin", jsonObj.getString("pin"));
                 map.put("tel", jsonObj.getString("tel"));
                 map.put("email", jsonObj.getString("email"));
                 map.put("postalAddress", jsonObj.getString("postalAddress"));
                 map.put("joined", LocalDate.parse(jsonObj.getString("joined"), DATE_FORMAT_yyyyMMdd));
                 map.put("lastUpdate", LocalDateTime.now());//.format(DATE_FORMAT_yyyyMMddHHmm)); //LocalDateTime.parse(jsonObj.getString("lastUpdate"), DATE_FORMAT_yyyyMMddHHmm));

                 mapList.add(map);

             }

             Result<List<Corporate>> ar = corpService.create(mapList,getUser());

             if(ar.isSuccess()){
                 return getJsonSuccessData(ar.getData());
             } else {
                 return getJsonErrorMsg(ar.getMsg());
             }

        }
    }

    @RequestMapping(value = "/update", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public String update(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request
    ) {
        JsonObject jsonObj = parseJsonObject(jsonData);

        String joinDateVal = jsonObj.getString("joined");
        LocalDateTime lastUpdateVal = LocalDateTime.now();

        Long idCorp = ((JsonNumber) jsonObj.get("idCorporate")).longValue();

        Result<Corporate> ar = corpService.update(
                idCorp,
                jsonObj.getString("name"),
                jsonObj.getString("abbreviation"),
                jsonObj.getString("pin"),
                jsonObj.getString("tel"),
                jsonObj.getString("email"),
                jsonObj.getString("postalAddress"),
                LocalDate.parse(joinDateVal, DATE_FORMAT_yyyyMMdd),
                lastUpdateVal,
                getUser());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }


    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(HttpServletRequest request) {

        //logger.info("Showing page param: " + request.getParameter("page"));
        // The hack below was to sidestep null error when editing a corporate.
        // TODO establish why during editing of the record, Extjs does a round trip to the back-end.
        int pageNo = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<Corporate>> ar = corpService.findAll(pageNo,size,getUser());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String delete(@RequestParam(value = "data", required = true) String jsonData,
                         HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idCorp = Long.valueOf(jsonObj.getString("idCorporate"));

        Result<Corporate> ar = corpService.remove(idCorp, getUser());

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
