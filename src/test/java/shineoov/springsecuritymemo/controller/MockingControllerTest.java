package shineoov.springsecuritymemo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = {MockingController.class})
public class MockingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("기본 유저정보")
    void basicMocking() throws Exception {
        String username = "shine";
        mockMvc.perform(get("/mocking/all").with(user(username)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("username").value(username));
    }

    @Test
    @DisplayName("기본 유저정보 2")
    void basicMocking2() throws Exception {
        mockMvc.perform(get("/mocking/all")
                        .with(user("shine").password("1234").roles("USER", "ADMIN"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("shine"))
                .andExpect(jsonPath("password").value("1234"))
                .andExpect(jsonPath("authorities", hasSize(2)))
                .andDo(print());
    }

    @Test
    @DisplayName("어노테이션 사용")
    @WithMockUser(username = "shine", roles = {"USER", "ADMIN"})
    void useAnnotation_basicMocking() throws Exception {
        mockMvc.perform(get("/mocking/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("shine"))
                .andExpect(jsonPath("authorities", hasSize(2)))
                .andDo(print());
    }

    @Test
    @DisplayName("조건없이 접근 성공")
    void ok_when_everyone() throws Exception {
        mockMvc.perform(get("/mocking/all")
                        .with(anonymous())
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("인증 후 접근시 성공")
    void ok_when_authentication() throws Exception {
        mockMvc.perform(get("/mocking/user")
                        .with(user("shine"))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("인증 없이 접근시 리아디렉트")
    void redirect_when_without_authentication() throws Exception {
        mockMvc.perform(get("/mocking/user")
                        .with(anonymous())
                )
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("권한이 없을때 403")
    void forbidden_when_notAuthority() throws Exception {
        mockMvc.perform(get("/mocking/admin")
                        .with(user("shine").roles("USER"))
                ) //Forbidden
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("권한 있을경우 접근 가능")
    void ok_when_hasPermission() throws Exception {
        mockMvc.perform(get("/mocking/admin")
                        .with(user("shine").roles("ADMIN"))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
