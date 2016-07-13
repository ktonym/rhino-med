package ke.co.rhino.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by akipkoech on 21/04/2016.
 */
@Component
public class CustomLogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication auth)
        {
        if(auth != null){
            if(logger.isDebugEnabled()) {
                logger.debug("----------Logout request received-------------");
                logger.debug("Principal: " + auth.getPrincipal()); //TODO clean this
            }
            new SecurityContextLogoutHandler().logout(request, response,auth);
        }

    }
}
