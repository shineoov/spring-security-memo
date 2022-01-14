package shineoov.springsecuritymemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shineoov.springsecuritymemo.domain.AccountDto;
import shineoov.springsecuritymemo.domain.AccountService;
import shineoov.springsecuritymemo.security.service.CustomUserDetails;

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

    @ResponseBody
    @GetMapping("/accounts/info")
    public Object info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails;
    }

}
