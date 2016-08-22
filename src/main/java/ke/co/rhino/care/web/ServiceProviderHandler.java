package ke.co.rhino.care.web;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.care.service.IProviderService;
import ke.co.rhino.security.IAuthenticationFacade;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.web.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 17/03/2016.
 */
@Controller
@RequestMapping("/care/provider")
public class ServiceProviderHandler extends AbstractHandler {

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String create(@RequestParam(value = "data", required = true) String jsonData, HttpServletRequest request){

        JsonObject jsonObj = parseJsonObject(jsonData);

        Result<ServiceProvider> ar = providerService.create(
                                jsonObj.getString("providerName"),
                                jsonObj.getString("email"),
                                jsonObj.getString("town"),
                                jsonObj.getString("tel"),
                                getUser());


        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/update", produces = {"application/json"}, method = RequestMethod.POST)
    @ResponseBody
    public String update(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request
    ) {
        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idServiceProvider = Long.valueOf(jsonObj.getString("idProvider"));

        Result<ServiceProvider> ar = providerService.update(
                idServiceProvider,
                jsonObj.getString("providerName"),
                jsonObj.getString("email"),
                jsonObj.getString("town"),
                jsonObj.getString("tel"),
                getUser());


        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }

    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(HttpServletRequest request){

        int page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
        Integer size = request.getParameter("limit") == null ? 5 : Integer.valueOf(request.getParameter("limit"));

        Result<Page<ServiceProvider>> ar = providerService.findAll(page,size,getUser());

        if (ar.isSuccess()) {
            return getJsonSuccessData(ar.getData());
        } else {
            return getJsonErrorMsg(ar.getMsg());
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String delete(@RequestParam(value = "data", required = true) String jsonData,
                         HttpServletRequest request) {

        JsonObject jsonObj = parseJsonObject(jsonData);

        Long idServiceProvider = Long.valueOf(jsonObj.getString("idServiceProvider"));

        Result<ServiceProvider> ar = providerService.delete(idServiceProvider, getUser());

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
