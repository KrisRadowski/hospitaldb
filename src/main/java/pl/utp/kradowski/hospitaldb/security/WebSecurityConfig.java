package pl.utp.kradowski.hospitaldb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.utp.kradowski.hospitaldb.entity.HospitalDBUser;
import pl.utp.kradowski.hospitaldb.repository.HospitalDBUserRepositoryImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/login";

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        /*http.csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and().authorizeRequests()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);*/
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",
                "/frontend/**",
                "/webjars/**",
                "/frontend-es5/**","/frontend-es6/**");
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {

            @Autowired
            private HospitalDBUserRepositoryImpl usersRepository;

            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                HospitalDBUser hospitalDBUser = usersRepository.findByHospitalDBUsername(s);
                if (hospitalDBUser == null) {
                    throw new UsernameNotFoundException(s);
                }
                return new HospitaldbUserDetails(hospitalDBUser);
            }
        };
    }
}
