package ke.co.rhino.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * Created by akipkoech on 23/02/2016.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("ke.co.rhino.security")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    Http401AuthenticationEntryPoint entryPoint;

    @Autowired
    AuthenticationSuccess successHandler;

    @Autowired
    AuthenticationFailure failureHandler;

    @Autowired
    CustomLogoutHandler logoutHandler;

    @Autowired
    LogoutSuccess logoutSuccess;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(entryPoint)
                .and()
                    .logout().logoutUrl("/logout").addLogoutHandler(logoutHandler)
                    .clearAuthentication(true).logoutSuccessHandler(logoutSuccess)
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/bootstrap.**", "/ext/**", "/app.js", "/app/**", "/login", "/build/**")
                    .permitAll().anyRequest()
                    .authenticated()
                .and().formLogin().loginProcessingUrl("/login").successHandler(successHandler)
                    .failureHandler(failureHandler).and()
//                        .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                    .csrf().disable()
                .sessionManagement().maximumSessions(1); // ensure no more than one login occurs for a user
                //.csrf().csrfTokenRepository(csrfTokenRepository());

               /* .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/bootstrap.**", "/ext/**", "/app.js", "/app/**", "/login", "/build/**").permitAll()
                    .anyRequest().fullyAuthenticated().and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                    .formLogin()
                    .successHandler(successHandler);*/
                    //.failureHandler(failureHandler).loginProcessingUrl("/login")
//
//                    ;
//                .and()
//                .logout();
//                .authorizeRequests()
//                .antMatchers("/css/**","/index.html","/").permitAll()
//                .anyRequest().fullyAuthenticated()
//                .and().httpBasic().authenticationEntryPoint(entryPoint);

                //.and()
                //.formLogin();
    }

    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        @Autowired
        CustomUserDetailsContextMapper contextMapper;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .ldapAuthentication()
                    .userDnPatterns("uid={0},ou=people")//bind configuration
                    .groupSearchBase("ou=groups")
                    //.contextSource().url("ldap://springframework.org:389/dc=springframework,dc=org");
                    .contextSource().ldif("classpath:test-server.ldif")
                    .and().userDetailsContextMapper(contextMapper)
                    .groupRoleAttribute("cn")//;//if prefixing the roles in the app, see below
                    .rolePrefix("ROLE_");
        }
    }

    private CsrfTokenRepository csrfTokenRepository(){
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TOKEN");
        return repository;
    }


}


/*

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

     @Value("${ldap.domain}")
     private String DOMAIN;

     @Value("${ldap.url}")
     private String URL;

     @Value("${http.port}")
     private int httpPort;

     @Value("${https.port}")
     private int httpsPort;

     @Override
     protected void configure(HttpSecurity http) throws Exception {

         //  Set up your spring security config here. For example...

             http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginUrl("/login").permitAll();

        //   Use HTTPs for ALL requests

        http.requiresChannel().anyRequest().requiresSecure();
        http.portMapper().http(httpPort).mapsTo(httpsPort);
        }

@Override
protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authManagerBuilder.authenticationProvider(activeDirectoryLdapAuthenticationProvider()).userDetailsService(userDetailsService());
        }

@Bean
public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(activeDirectoryLdapAuthenticationProvider()));
        }
@Bean
public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
        ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(DOMAIN, URL);
        provider.setConvertSubErrorCodesToExceptions(true);
        provider.setUseAuthenticationRequestCredentials(true);
        return provider;
        }
        }


 */
