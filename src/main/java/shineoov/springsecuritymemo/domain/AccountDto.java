package shineoov.springsecuritymemo.domain;

import lombok.Data;

@Data
public class AccountDto {
    private String email;
    private String password;
    private String role;
}
