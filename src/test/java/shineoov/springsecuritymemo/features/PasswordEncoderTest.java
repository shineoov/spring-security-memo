package shineoov.springsecuritymemo.features;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Test
    @DisplayName("기본 password encoder 사용")
    void basicPasswordEncodeTest() {
        //given
        String rawPassword = "INPUT_PASSWORD";

        //when
        String encodePassword = passwordEncoder.encode(rawPassword);
        boolean isMatch = passwordEncoder.matches(rawPassword, encodePassword);

        //then
        assertThat(isMatch).isTrue();
    }

    @Test
    @DisplayName("암호화 사용 X")
    void matchNonPasswordEncodeTest1() {
        //given
        String rawPassword = "password12";
        String encodePassword = "{noop}password12";

        //when
        boolean isMatch = passwordEncoder.matches(rawPassword, encodePassword);

        //then
        assertThat(isMatch).isTrue();
    }

    @Test
    @DisplayName("다른 암호화 사용 비밀번호 ")
    void matchAnotherPasswordEncodeTest() {
        // given
        PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

        //when
        String standardPassword = pbkdf2PasswordEncoder.encode("AAA");
        boolean isMatch = passwordEncoder.matches("AAA", "{pbkdf2}" + standardPassword);

        //then
        assertThat(isMatch).isTrue();
    }
}
