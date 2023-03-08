package hello.ui.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/manage")
public class ManageController {

    @GetMapping("/signup")
    public String adminPage() {
        return "user/signup";
    }
}
