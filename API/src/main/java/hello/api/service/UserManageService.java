package hello.api.service;

import hello.api.dto.UserInformationDto;
import hello.api.dto.UserSignupRequest;
import hello.api.entity.Users;
import hello.api.enumforexception.UserManageExceptionEnum;
import hello.api.exception.UserManageException;
import hello.api.jwt.PrincipalDetails;
import hello.api.repository.UserRepository;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserSignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserManageException(
                UserManageExceptionEnum.DUPLICATED_SIGNUP_EMAIL.getErrormessage());
        }
        Users users = Users.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .roles("ROLE_USER")
            .build();
        userRepository.save(users);
    }

    @Transactional(readOnly = true)
    public UserInformationDto getUserInformation(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        UserInformationDto userInformationDto = new UserInformationDto(
            principal.getUser().getUsername(), principal.getUser().getName());
        return userInformationDto;
    }
}
