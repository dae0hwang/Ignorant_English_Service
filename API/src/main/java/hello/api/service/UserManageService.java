package hello.api.service;

import hello.api.dto.UserInformationDto;
import hello.api.dto.UserSignupRequest;
import hello.api.entity.EmailAuth;
import hello.api.entity.Users;
import hello.api.enumforexception.UserManageExceptionEnum;
import hello.api.exception.UserManageException;
import hello.api.jwt.PrincipalDetails;
import hello.api.repository.EmailAuthRepository;
import hello.api.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserManageService {

    private final UserRepository userRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserSignupRequest request) {
        //users에 아이디 존재, 이메일 인증 여부는 false 그리고 emailAuth 이메일 존재하고 ,만료된 인증 정보일 때
        //재가입을 시켜준다
        rejoinExpiredAuthUser(request);

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserManageException(
                UserManageExceptionEnum.DUPLICATED_SIGNUP_EMAIL.getErrormessage());
        }
        Users users = Users.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .emailAuth(false)
            .roles("ROLE_USER")
            .build();
        userRepository.save(users);
    }

    private void rejoinExpiredAuthUser(UserSignupRequest request) {
        Optional<Users> byUsernameAndEmailAuthFalse = userRepository.findByUsernameAndEmailAuthFalse(
            request.getUsername());
        Optional<EmailAuth> byEmailAndExpiredTrue = emailAuthRepository.findByEmailAndExpiredTrue(
            request.getUsername());
        if (byUsernameAndEmailAuthFalse.isPresent() && byEmailAndExpiredTrue.isPresent()) {
            log.info("재가입 조건 충족");
            userRepository.delete(byUsernameAndEmailAuthFalse.orElseThrow());
            emailAuthRepository.delete(byEmailAndExpiredTrue.orElseThrow());
        }
    }

    @Transactional(readOnly = true)
    public UserInformationDto getUserInformation(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        boolean emailAuth = findEmailAuth(principal);
        Users findUser = userRepository.findByUsername(principal.getUser().getUsername())
            .orElseThrow();
        return new UserInformationDto(
            principal.getUser().getUsername(), findUser.getId(), principal.getUser().getName(),
            emailAuth);
    }

    private boolean findEmailAuth(PrincipalDetails principal) {
        Users users = userRepository.findByUsername(principal.getUsername()).orElseThrow();
        return users.getEmailAuth();
    }
}
