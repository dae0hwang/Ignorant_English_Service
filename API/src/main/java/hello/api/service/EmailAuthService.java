package hello.api.service;

import hello.api.entity.EmailAuth;
import hello.api.repository.EmailAuthRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final EmailAuthRepository emailAuthRepository;

    @Transactional
    public EmailAuth save(String email) {
        EmailAuth emailAuth = EmailAuth.builder()
            .email(email)
            .authToken(UUID.randomUUID().toString())
            .expired(false)
            .build();
        return emailAuthRepository.save(emailAuth);
    }
}
