package hello.api.service;

import hello.api.entity.EmailAuth;
import hello.api.repository.EmailAuthRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class EmailAuthServiceTest {

    @Autowired
    EmailAuthService emailAuthService;
    @Autowired
    EmailAuthRepository emailAuthRepository;

    @Test
    void save() {
        //given
        String requestEmail = "asdf123@naver.com";
        //when
        EmailAuth saveEntity = emailAuthService.save(requestEmail);
        //then
        EmailAuth emailAuth = emailAuthRepository.findAll().get(0);
        Assertions.assertThat(emailAuth).isEqualTo(saveEntity);
    }
}