package ke.co.rhino.security;

import ke.co.rhino.uw.web.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by user on 29-Jan-17.
 */
@Controller
@RequestMapping("/session")
public class SessionHandler extends AbstractHandler{

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String testSession(HttpServletRequest request){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        String username = authenticationFacade.getAuthentication().getName();

        if(username!=null){
            builder.add("success",true);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            List<String> roles = authenticationFacade.getAuthentication().getAuthorities().stream()
                    .map(g -> g.getAuthority())
                    .collect(Collectors.toList());

            for(String role: roles){
                arrayBuilder.add(
                        Json.createObjectBuilder()
                                .add("roleName", role));
            };


            builder.add("username",username)
                    .add("roles", arrayBuilder);

        } else {
            builder.add("success",false);
        }
        return toJsonString(builder.build());
    }

}
