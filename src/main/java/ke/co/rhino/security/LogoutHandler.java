package ke.co.rhino.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by akipkoech on 24/02/2016.
 */
//@Controller
public class LogoutHandler{

    //@RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            System.out.println("----------Logout request received-------------");
            System.out.println("Principal: " + auth.getPrincipal().toString()); //TODO clean this
            new SecurityContextLogoutHandler().logout(request, response,auth);
        }
        return "Successful logout";
    }
}
