package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.CategoryPrincipal;
import ke.co.rhino.uw.service.ICategoryPrincipalService;
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

/**
 * Created by user on 7/16/2016.
 */
@Controller
@RequestMapping("/uw/categoryprincipal")
public class CategoryPrincipalHandler extends AbstractHandler {
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private ICategoryPrincipalService service;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(@RequestParam(value = "data")String jsonData, HttpServletRequest request){

        JsonObject jsonObject=parseJsonObject(jsonData);

        Long idCategory = ((JsonNumber) jsonObject.get("idCategory")).longValue();
        Long idPrincipal = ((JsonNumber) jsonObject.get("idPrincipal")).longValue();
        String wefStr = jsonObject.getString("wef");
        LocalDate wef = LocalDate.parse(wefStr,DATE_FORMAT_yyyyMMdd);

        Result<CategoryPrincipal> ar = service.create(idCategory,idPrincipal,wef,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public String update(@RequestParam(value = "data")String jsonData, HttpServletRequest request){

        JsonObject jsonObject=parseJsonObject(jsonData);

        Long idCategory = ((JsonNumber) jsonObject.get("idCategory")).longValue();
        Long idPrincipal = ((JsonNumber) jsonObject.get("idPrincipal")).longValue();
        String wefStr = jsonObject.getString("wef");
        LocalDate wef = LocalDate.parse(wefStr,DATE_FORMAT_yyyyMMdd);
        Boolean status = jsonObject.getString("active")=="Y";

        Result<CategoryPrincipal> ar = service.update(idCategory,idPrincipal,wef,status,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST,produces = {"application/json"})
    @ResponseBody
    public String delete(@RequestParam(value = "data")String jsonData, HttpServletRequest request){

        JsonObject jsonObject = parseJsonObject(jsonData);
        Long idCategory = ((JsonNumber) jsonObject.get("idCategory")).longValue();
        Long idPrincipal = ((JsonNumber) jsonObject.get("idPrincipal")).longValue();

        Result<CategoryPrincipal> ar = service.remove(idCategory,idPrincipal,getUser());
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

//    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
//    @ResponseBody
//    public String findAll(HttpServletRequest request){
//
//    }

    public String getUser(){
        return authenticationFacade.getAuthentication().getName();
    }
}
