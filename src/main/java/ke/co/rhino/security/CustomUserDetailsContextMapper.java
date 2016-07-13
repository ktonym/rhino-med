package ke.co.rhino.security;

import ke.co.rhino.entity.AppUser;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by akipkoech on 24/02/2016.
 */
@Component("contextMapper")
public class CustomUserDetailsContextMapper implements UserDetailsContextMapper{
    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
                                          Collection<? extends GrantedAuthority> authorities) {
        AppUser user = new AppUser();
        user.setFirstName(ctx.getStringAttribute("sn")); //could be givenName, if givenName exists
        user.setLastName(ctx.getStringAttribute("sn"));
        user.setEmail(ctx.getStringAttribute("email"));
        user.setUsername(username);
        user.setAuthorities(authorities);
        return user;
    }

    @Override
    public void mapUserToContext(UserDetails userDetails, DirContextAdapter ctx) {
        AppUser appUser = (AppUser) userDetails;
        ctx.setAttributeValue("sn",appUser.getLastName());
        ctx.setAttributeValue("email", appUser.getEmail());
        ctx.setAttributeValue("uid", appUser.getUsername());
    }
}
