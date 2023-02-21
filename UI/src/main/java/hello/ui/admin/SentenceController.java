package hello.ui.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/sentence")
public class SentenceController {

    @GetMapping("/add")
    public String sentenceAdd() {
        return "admin/sentenceAdd";
    }

    @GetMapping("/list")
    public String sentenceList() {
        return "admin/sentenceList";
    }

    @GetMapping("/update/{id}")
    public String sentenceUpdate(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "admin/sentenceUpdate";
    }
}
