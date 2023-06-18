package hello.plusapi.service;

import hello.plusapi.dto.KafkaEmailDto;
import hello.plusapi.entity.EmailAuth;
import hello.plusapi.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("server")
public class KafkaSqsEmailService implements SqsEmailService<KafkaEmailDto> {

    @Value("${server.url}")
    private String serverUrl;
    private static final String TOPIC = "email";
    private final JavaMailSender javaMailSender;
    private final EmailAuthRepository emailAuthRepository;

    @Override
    @KafkaListener(topics = TOPIC, groupId = "foo", properties = {
        "spring.json.value.default.type:hello.plusapi.dto.KafkaEmailDto"})
    public void sendEmailAuth(KafkaEmailDto kafkaEmailDto) {
        EmailAuth emailAuth = emailAuthRepository.findByEmail(kafkaEmailDto.getEmail())
            .orElseThrow();
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(kafkaEmailDto.getEmail());
        smm.setSubject("회원 가입 이메일 인증");
        String linkText = makeLinkText(emailAuth.getAuthToken());
        smm.setText(linkText);
        javaMailSender.send(smm);
    }

    private String makeLinkText(String authToken) {
        return "http://" + serverUrl + "/api/email/auth" + "?authToken=" + authToken;
    }
}
