package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.Category;
import ke.co.rhino.uw.service.ICategoryService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by akipkoech on 25/04/2016.
 */
@Controller
@RequestMapping("/uw/category")
public class CategoryHandler extends AbstractHandler{

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private ICategoryService categoryService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(
            @RequestParam(value = "data",required = true) String jsonData,
            HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idCorpAnniv = ((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
        String cat = jsonObj.getString("cat");
        String description = jsonObj.getString("description");
        Result<Category> ar = categoryService.create(idCorpAnniv,cat,description);
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
            HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idCat = ((JsonNumber) jsonObj.get("idCategory")).longValue();
        Long idCorpAnniv = ((JsonNumber) jsonObj.get("idCorpAnniv")).longValue();
        String cat = jsonObj.getString("cat");
        String description = jsonObj.getString("description");

        Result<Category> ar = categoryService.update(idCat,idCorpAnniv,cat,description);
        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);
        Long idCat = ((JsonNumber) jsonObj.get("idCategory")).longValue();
        Result<Category> ar = categoryService.remove(idCat, getUser());
        if(ar.isSuccess()){
            return getJsonSuccessMsg(ar.getMsg());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(
            @RequestParam(value = "idCorpAnniv", required = true) String idCorpAnnivStr,
            HttpServletRequest request){

        Long idCorpAnniv = Long.parseLong(idCorpAnnivStr);
        Result<List<Category>> ar = categoryService.findByAnniv(idCorpAnniv);
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
