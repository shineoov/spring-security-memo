package shineoov.springsecuritymemo.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import shineoov.springsecuritymemo.domain.Account;

import java.util.Collection;

public class AccountAdapter extends User {

    private final Account account;

    public AccountAdapter(Account account, Collection<? extends GrantedAuthority> authorities) {
        super(account.getEmail(), account.getPassword(), authorities);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
