package ke.co.rhino.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by akipkoech on 22/02/2016.
 */
@Component
public class AuthenticationSuccess implements AuthenticationSuccessHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //ControllerUtils.clearCurrentUser(request.getSession());

        if (logger.isDebugEnabled()){
            logger.debug("Successful login for " + request.getParameter("username"));
        }
        logger.info("Successful login for " + request.getParameter("username"));

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control","no-cache");


        //List roles = (List) authentication.getAuthorities();

        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        //logger.info(roles.toString());

        String authList = authorities.stream()
                .map(auth -> auth.getAuthority())
                .reduce((t,u) -> t + "," + u )
                .get();

        try(Writer out = response.getWriter();){
            out.write(String.format("{\"%s\": %b, \"%s\": \"%s\"}","success", true,"msg", "Login success for " + request.getParameter("username")));
            logger.info(authList);
        } catch (IOException e){
            logger.error("Failed to write to response", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"There was a processing error.");
        }
        /*finally {
            out.close();
        }*/

    }
}
