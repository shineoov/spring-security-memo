package shineoov.springsecuritymemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests
                .mvcMatchers("/").permitAll()
                .anyRequest().authenticated()
        );

        http.httpBasic();

        configureFormLogin(http);

        configureLogout(http);

        configureSession(http);
    }

    private void configureLogout(HttpSecurity http) throws Exception {
        //https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html
        http.logout(logout -> logout
                .deleteCookies("cookie1", "cookie2")
                .logoutSuccessHandler((request, response, authentication) -> {
                    log.info("[LOGOUT] authentication={}", authentication);
                    response.sendRedirect("/?referer=logout");
                })
        );
    }

    private void configureSession(HttpSecurity http) throws Exception {
        http.sessionManagement(session -> session
                .maximumSessions(1) // 최대 Session 수
                .maxSessionsPreventsLogin(false) // true -> 추가 로그인 X
        );
    }

    private void configureFormLogin(HttpSecurity http) throws Exception {
        http.formLogin(form -> form
                //.loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {
                    log.info("[LoginSuccess] name={}", authentication.getName());
                    log.info("[LoginSuccess] principal={}", authentication.getPrincipal());
                })
                .failureHandler((request, response, exception) -> {
                    log.info("[LoginFail] exceptionMessage={}", exception.getMessage());
                })
                .permitAll()
        );
    }
}
