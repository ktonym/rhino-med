package ke.co.rhino.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by akipkoech on 22/02/2016.
 */
@Component
public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {

        if (logger.isDebugEnabled()){
            logger.debug("url requires authentication: " + request.getRequestURL().toString());
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Authentication required");
    }
}
