package shineoov.springsecuritymemo.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests.anyRequest().authenticated());
        http.formLogin(form -> form
                .loginPage("/login")
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
