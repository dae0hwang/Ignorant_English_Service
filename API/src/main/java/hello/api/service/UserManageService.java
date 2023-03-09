package hello.api.service;

import hello.api.dto.UserSignupRequest;
import hello.api.entity.Users;
import hello.api.enumforexception.UserManageExceptionEnum;
import hello.api.exception.UserManageException;
import hello.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserManageException(
                UserManageExceptionEnum.DUPLICATED_SIGNUP_EMAIL.getErrormessage());
        }
        Users users = Users.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .name(request.getName())
            .authority("ROLE_USER")
            .build();
        userRepository.save(users);
    }


}
