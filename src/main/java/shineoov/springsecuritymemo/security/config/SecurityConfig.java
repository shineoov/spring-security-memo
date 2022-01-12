package shineoov.springsecuritymemo.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests
                .mvcMatchers("/", "/accounts/signup").permitAll()
                .anyRequest().authenticated()
        );
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
                .defaultSuccessUrl("/")
                .failureHandler((request, response, exception) -> {
                    log.info("[LoginFail] exceptionMessage={}", exception.getMessage());
                })
                .failureUrl("/login?error")
                .permitAll()
        );
    }
}
