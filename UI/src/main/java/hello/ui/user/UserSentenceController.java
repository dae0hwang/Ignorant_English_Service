package hello.ui.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/sentence")
public class UserSentenceController {

    @GetMapping("/page/{groupId}")
    public String userSentencePage(Long groupId) {
        return "user/sentence/page";
    }
}
