package ke.co.rhino.uw.web;

import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.entity.ContactPerson;
import ke.co.rhino.uw.service.IContactPersonService;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by akipkoech on 03/02/2016.
 */
@Controller
@RequestMapping("/uw/contactperson")
public class ContactPersonHandler extends AbstractHandler {

    @Autowired
    private IContactPersonService contactPersonService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findContact(
            @RequestParam(value ="idCorporate", required = true) Long idCorporate,
            HttpServletRequest request ){

        //User sessionUser = getSessionUser(request);

        Result<List<ContactPerson>> ar = contactPersonService.findAll(idCorporate, getUser());

        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }


    @RequestMapping(value = "/store", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String update(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request){

        //User sessionUser = getSessionUser(request);

        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idContactPerson = Long.valueOf(jsonObj.getString("idContactPerson"));
        Long idCorporate = Long.valueOf(jsonObj.getString("idCorporate"));
                Result< ContactPerson > ar = contactPersonService.store(
                        idContactPerson,
                        idCorporate,
                        jsonObj.getString("firstName"),
                        jsonObj.getString("surname"),
                        jsonObj.getString("jobTitle"),
                        jsonObj.getString("email"),
                        jsonObj.getString("tel"),
                        getUser());
        //sessionUser.getUsername());


        if(ar.isSuccess()){
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "idContactPerson", required = true) Long idContactPerson,
            HttpServletRequest request){

        //User sessionUser = getSessionUser(request);

        Result<ContactPerson> ar = contactPersonService.remove(idContactPerson, getUser());

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
