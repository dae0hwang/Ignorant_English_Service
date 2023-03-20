package hello.plusapi;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final JavaMailSender javaMailSender;

    @GetMapping("/smtp")
    public String smtp() {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo("geungan9@naver.com");
        smm.setSubject("회원가입 이메일 인증");
        smm.setText("gogo");

        javaMailSender.send(smm);
        return "success";
    }
}
