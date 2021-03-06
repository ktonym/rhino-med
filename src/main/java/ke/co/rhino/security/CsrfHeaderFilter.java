package ke.co.rhino.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by akipkoech on 25/02/2016.
 */
public class CsrfHeaderFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if( csrf != null ){
            Cookie cookie = WebUtils.getCookie(request, "CSRF-TOKEN");
            String token = csrf.getToken();
            if (cookie==null || token!=null && !token.equals(cookie.getValue())){
                cookie = new Cookie("CSRF-TOKEN", token);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        chain.doFilter(request,response);
    }


}
