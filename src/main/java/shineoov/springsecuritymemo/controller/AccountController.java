package shineoov.springsecuritymemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shineoov.springsecuritymemo.domain.AccountDto;
import shineoov.springsecuritymemo.domain.AccountService;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/accounts/signup")
    public String join() {
        return "/accounts/join";
    }

    @PostMapping("/accounts/signup")
    public String join(@ModelAttribute AccountDto accountDto) {
        accountService.save(accountDto);
        return "redirect:/";
    }
}
