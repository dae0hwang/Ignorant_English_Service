package hello.api.service;

import hello.api.dto.UserInformationDto;
import hello.api.dto.UserSignupRequest;
import hello.api.entity.EmailAuth;
import hello.api.entity.Users;
import hello.api.exception.UserManageException;
import hello.api.jwt.PrincipalDetails;
import hello.api.repository.EmailAuthRepository;
import hello.api.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    EmailAuthRepository emailAuthRepository;

    @Test
    @DisplayName("회원 가입 성공")
    void signup1() {
        //given
        UserSignupRequest userSignupRequest = new UserSignupRequest("email@naver.com", "password",
            "name");
        //when
        userManageService.signup(userSignupRequest);
        //then
        Users users = userRepository.findByUsername("email@naver.com").orElseThrow();
        Assertions.assertThat(users.getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("회원 가입 실패 - 이미 존재하는 아이디")
    void signup2() {
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

    @Test
    @DisplayName("재 회원 가입 성공 - 이메일 인증 만료날짜 지나서")
    void signup3() {
        //given
        userRepository.save(
            Users.builder().username("email@naver.com").password("password").name("name")
                .roles("ROLE_USER").emailAuth(false).build());
        emailAuthRepository.save(
            EmailAuth.builder().email("email@naver.com").authToken("token").expired(true).build()
        );
        //when
        userManageService.signup(new UserSignupRequest("email@naver.com", "password", "newName"));
        //then
        Users rejoinUser = userRepository.findByUsername("email@naver.com").orElseThrow();
        Assertions.assertThat(rejoinUser.getName()).isEqualTo("newName");
    }

    @Test
    @DisplayName("회원 정보 흭득 성공")
    void getUserInformation() {
        //given
        Users save = userRepository.save(
            Users.builder().username("email@naver.com").password("password").name("name")
                .emailAuth(false).roles("ROLE_USER").build());
        //security ContextHolder에 있는 유저 정보 가져오는 상황 강제로 연출
        PrincipalDetails principalDetails = new PrincipalDetails(
            Users.builder().username("email@naver.com").password("password").name("name")
                .emailAuth(false).roles("ROLE_USER").build());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            principalDetails,
            null,
            principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //when
        UserInformationDto findUserInformationDto = userManageService.getUserInformation(
            authentication);
        //then
        UserInformationDto actualDto = new UserInformationDto(save.getUsername(), save.getId(),
            save.getName(), save.getEmailAuth());
        Assertions.assertThat(findUserInformationDto).isEqualTo(actualDto);
    }
}