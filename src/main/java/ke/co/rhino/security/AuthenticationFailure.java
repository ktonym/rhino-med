package ke.co.rhino.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by akipkoech on 22/02/2016.
 */
@Component
public class AuthenticationFailure implements AuthenticationFailureHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        if (logger.isDebugEnabled()){
            logger.debug("Failed login for " + request.getParameter("username"));
        }

        logger.info("Failed login for " + request.getParameter("username"));

        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        Writer out = response.getWriter();
        String failureMsg = "Login failed for " + request.getParameter("username");

        try{
            out.write(String.format("{\"%s\": %b,\"%s\": \"%s\" }" ,"success", false, "msg", failureMsg ));
            //out.write("{success:false, message: Invalid username/password combination }");
        } catch (IOException ioe){
            logger.error("Failed to write to response", ioe);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "There was a processing error");
        } finally {
            out.close();
        }

    }
}
