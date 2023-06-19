package hello.plusapi.service;

import hello.plusapi.entity.EmailAuth;
import hello.plusapi.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile({"aws","local"})
public class AwsSqsEmailService implements SqsEmailService<String>{

    @Value("${server.url}")
    private String serverUrl;
    private final EmailAuthRepository emailAuthRepository;
    private final JavaMailSender javaMailSender;

    @SqsListener("sqs")
    @Override
    public void sendEmailAuth(String email) {
        EmailAuth emailAuth = emailAuthRepository.findByEmail(email)
            .orElseThrow();
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(email);
        smm.setSubject("회원 가입 이메일 인증");
        String linkText = makeLinkText(emailAuth.getAuthToken());
        smm.setText(linkText);
        javaMailSender.send(smm);
        log.info(serverUrl);
    }

    private String makeLinkText(String authToken) {
        return "http://" + serverUrl + "/api/email/auth" + "?authToken=" + authToken;
    }
}
