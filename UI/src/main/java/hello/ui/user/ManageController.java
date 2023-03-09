package hello.ui.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/manage")
public class ManageController {

    @GetMapping("/signup")
    public String userPage() {
        return "user/signup";
    }

    @GetMapping("/login")
    public String userLogin() {
        return "user/login";
    }

    @GetMapping("/account")
    public String userAccount() {
        return "user/account";
    }
}
