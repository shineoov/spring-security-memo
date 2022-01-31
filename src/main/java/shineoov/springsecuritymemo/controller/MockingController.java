package shineoov.springsecuritymemo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mocking")
public class MockingController {

    @GetMapping("/all")
    public Object all(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/user")
    public Object user(@AuthenticationPrincipal User user) {
        return user;
    }

    @GetMapping("/admin")
    public Object admin(@AuthenticationPrincipal User user) {
        return user;
    }
}
