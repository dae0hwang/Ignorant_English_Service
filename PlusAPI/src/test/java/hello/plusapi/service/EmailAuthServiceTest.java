package hello.plusapi.service;

import static org.junit.jupiter.api.Assertions.*;

import hello.plusapi.entity.EmailAuth;
import hello.plusapi.entity.Users;
import hello.plusapi.enumforexception.EmailAuthExceptionEnum;
import hello.plusapi.exception.EmailAuthException;
import hello.plusapi.repository.EmailAuthRepository;
import hello.plusapi.repository.UserRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EmailAuthServiceTest {

    @Autowired
    EmailAuthService emailAuthService;
    @Autowired
    EmailAuthRepository emailAuthRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("이메일 인증 성공")
    void confirmEmail1() {
        //given
        userRepository.save(
            Users.builder().username("email@naver.com").password("password").emailAuth(false)
                .name("name").roles(null).build());
        emailAuthRepository.save(
            EmailAuth.builder().authToken("authToken").email("email@naver.com").expired(false)
                .expireDate(Timestamp.valueOf(LocalDateTime.now().plusHours(1L))).build());
        entityManager.flush();
        entityManager.clear();
        //when
        emailAuthService.confirmEmail("authToken");
        //then
        EmailAuth findEmailAuth = emailAuthRepository.findByEmail("email@naver.com").orElseThrow();
        Users findUser = userRepository.findByUsername("email@naver.com").orElseThrow();
        Assertions.assertThat(findEmailAuth.getExpired()).isTrue();
        Assertions.assertThat(findUser.getEmailAuth()).isTrue();
    }
    @Test
    @DisplayName("이메일 인증 실패 - 존재하지 않은 emailAuth")
    void confirmEmail2() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> emailAuthService.confirmEmail("authToken"))
            .isInstanceOf(
                EmailAuthException.class)
            .hasMessage(EmailAuthExceptionEnum.NOEXIST_EMAILAUTHTOKEN.getErrormessage());
    }

    @Test
    @DisplayName("이메일 인증 실패 - 시간이 만료된 emailAuth")
    void confirmEmail3() {
        //given
        emailAuthRepository.save(
            EmailAuth.builder().email("email@naver.com").authToken("authToken").expired(false)
                .expireDate(
                    Timestamp.valueOf(LocalDateTime.now().minusMinutes(5L))).build());
        //when
        //then
        Assertions.assertThatThrownBy(() -> emailAuthService.confirmEmail("authToken"))
            .isInstanceOf(
                EmailAuthException.class)
            .hasMessage(EmailAuthExceptionEnum.EXPIRED_EMAILAUTHTOKEN.getErrormessage());
    }
}