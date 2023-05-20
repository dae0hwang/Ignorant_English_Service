package hello.plusapi.service;

import hello.plusapi.dto.KafkaEmailDto;
import hello.plusapi.entity.EmailAuth;
import hello.plusapi.entity.Users;
import hello.plusapi.enumforexception.EmailAuthExceptionEnum;
import hello.plusapi.exception.EmailAuthException;
import hello.plusapi.repository.EmailAuthRepository;
import hello.plusapi.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAuthService {

    @Value("${server.port:5510}")
    private String port;
    @Value("${api.ip:localhost}")
    private String ip;
    private static final String TOPIC = "email";
    private final JavaMailSender javaMailSender;
    private final EmailAuthRepository emailAuthRepository;
    private final UserRepository userRepository;

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
        return "http://" + ip + ":" + port + "/api/email/auth" + "?authToken=" + authToken;
    }

    @Transactional(noRollbackFor = {EmailAuthException.class})
    public void confirmEmail(String authToken) {
        Optional<EmailAuth> findOptionalExpiredAuth = emailAuthRepository.findExpiredAuth(authToken);
        if (findOptionalExpiredAuth.isPresent()) {
            EmailAuth emailAuth = findOptionalExpiredAuth.get();
            emailAuth.useToken();
            throw new EmailAuthException(
                EmailAuthExceptionEnum.EXPIRED_EMAILAUTHTOKEN.getErrormessage());
        } else {
            EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(authToken)
                .orElseThrow(() -> new EmailAuthException(
                    EmailAuthExceptionEnum.NOEXIST_EMAILAUTHTOKEN.getErrormessage()));
            Users users = userRepository.findByUsername(emailAuth.getEmail()).orElseThrow();
            users.emailVerifiedSuccess();
            emailAuth.useToken();
        }
    }
}
