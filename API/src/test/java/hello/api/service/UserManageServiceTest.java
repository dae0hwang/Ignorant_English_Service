package hello.api.service;

import hello.api.dto.UserSignupRequest;
import hello.api.entity.Users;
import hello.api.exception.UserManageException;
import hello.api.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserManageServiceTest {

    @Autowired
    UserManageService userManageService;
    @Autowired
    UserRepository userRepository;

    @Test
    void signup() {
        //given
        UserSignupRequest userSignupRequest = new UserSignupRequest("email@naver.com", "password",
            "name");
        //when
        userManageService.signup(userSignupRequest);
        //then
        Users users = userRepository.findByUsername("email@naver.com").get();
        Assertions.assertThat(users.getName()).isEqualTo("name");
    }

    @Test
    void signupException() {
        //given
        userRepository.save(
            Users.builder().username("email@naver.com").password("password").name("name")
                .roles("ROLE_USER").build());
        UserSignupRequest userSignupRequest = new UserSignupRequest("email@naver.com", "password",
            "name");
        //when
        //then
        Assertions.assertThatThrownBy(() -> userManageService.signup(userSignupRequest))
            .isInstanceOf(
                UserManageException.class);
    }
}