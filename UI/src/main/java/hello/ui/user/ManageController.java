package hello.ui.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/manage")
public class ManageController {

    @GetMapping("/signup")
    public String singup() {
        return "user/signup";
    }

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/account")
    public String accountPage() {
        return "user/account";
    }

    @GetMapping("/oauth")
    public String oauth(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "user/oauth";
    }
}
