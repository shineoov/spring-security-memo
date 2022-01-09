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
        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());

        // form login 설정
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
        http.httpBasic();

        //SESSION 설정
        http.sessionManagement(session -> session
                .maximumSessions(1) // 최대 Session 수
                .maxSessionsPreventsLogin(false) // true -> 추가 로그인 X
        );
    }
}
