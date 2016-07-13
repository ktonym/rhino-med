package ke.co.rhino.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
public class LogoutSuccess implements LogoutSuccessHandler {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Writer out = response.getWriter();
        String username = request.getParameter("username");
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Logout successful for " + username);
            }
            try{
                out.write(String.format("{\"%s\": %b, \"%s\": \"%s\"}","success", true,"msg", "Logout success for " + username));
                //logger.info(authList);
            } catch (IOException e){
                logger.error("Failed to write to response", e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"There was a processing error.");
            } finally {
                out.close();
            }
        } catch (Throwable e){
            logger.error("Processing failed", e);
        }

    }
}
