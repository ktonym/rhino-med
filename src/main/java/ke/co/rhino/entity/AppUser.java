package ke.co.rhino.entity;

import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.Collection;

/**
 * Created by akipkoech on 24/02/2016.
 */
@Entity
@Table(name = "APP_USER")
public class AppUser extends AbstractEntity implements UserDetails,EntityItem<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appUserId;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="USERNAME")
    private String username;

    @Column(name="EMAIL")
    private String email;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public Long getAppUserId() {
        return appUserId;
    }

//    public void setAppUserId(Long appUserId) {
//        this.appUserId = appUserId;
//    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
       builder.add("appUserId",appUserId)
               .add("firstName",firstName)
               .add("lastName",lastName)
               .add("username",username)
               .add("email",email);
    }

    @Override
    public Long getId() {
        return appUserId;
    }
}
