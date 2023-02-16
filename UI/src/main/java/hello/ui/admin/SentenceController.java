package hello.ui.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/sentence")
public class SentenceController {

    @GetMapping("/add")
    public String sentenceAdd() {
        return "admin/sentenceAdd";
    }
}
