package pl.utp.kradowski.hospitaldb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGOUT_SUCCESS_URL = "/login";
    private static final String LOGIN_SUCCESS_URL = "/403";

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and().authorizeRequests()
                .antMatchers("/register","/403").permitAll()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL).defaultSuccessUrl(LOGIN_SUCCESS_URL)
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
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
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return passwordEncoder;
    }
    /*@Override
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
    }*/
}
