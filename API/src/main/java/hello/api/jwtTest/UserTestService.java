package hello.api.jwtTest;

import hello.api.entity.Users;
import hello.api.repository.UserRepository;
import hello.api.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserTestService {

    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;

    @Transactional(readOnly = true)
    public Users getUserWithAuthorities(String username) {
        return userRepository.findByEmail(username).orElse(null);
    }

    @Transactional(readOnly = true)
    public Users getMyUserWithAuthorities() {
        return userRepository.findByEmail(securityUtil.getCurrentUsername().orElse(null)).orElse(null);
    }
}
