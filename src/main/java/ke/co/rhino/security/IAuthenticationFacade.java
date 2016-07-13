package ke.co.rhino.security;

import org.springframework.security.core.Authentication;

/**
 * Created by akipkoech on 25/04/2016.
 */
public interface IAuthenticationFacade {

    Authentication getAuthentication();

}
