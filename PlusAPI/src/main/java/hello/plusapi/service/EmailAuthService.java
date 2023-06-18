package hello.plusapi.service;

import hello.plusapi.entity.EmailAuth;
import hello.plusapi.entity.Users;
import hello.plusapi.enumforexception.EmailAuthExceptionEnum;
import hello.plusapi.exception.EmailAuthException;
import hello.plusapi.repository.EmailAuthRepository;
import hello.plusapi.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAuthService {

    private final EmailAuthRepository emailAuthRepository;
    private final UserRepository userRepository;

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
