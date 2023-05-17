package hello.ui.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/test")
public class UserTestController {

    @GetMapping("/admin/check")
    public String adminSentenceCheckPage() {
        return "test/admin/check";
    }

    //여기 컨트롤러로 들어와서 데이터 가지고 테스트 카드 페이지 시작한다.
    @GetMapping("/admin/page")
    public String adminSentenceTestPage() {
        return "test/admin/page";
    }
}
