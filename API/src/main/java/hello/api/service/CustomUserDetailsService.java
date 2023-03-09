package hello.api.service;

import hello.api.entity.Users;
import hello.api.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        return userRepository.findByEmail(username)
            .map(user -> createUser(user))
            .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }

    private org.springframework.security.core.userdetails.User createUser(Users user) {
        //활성화 부분 제거
        List<GrantedAuthority> grantedAuthorities =Arrays.asList(
            new SimpleGrantedAuthority(user.getAuthority()));
//            List < GrantedAuthority > grantedAuthorities = Arrays.asList(
//                new SimpleGrantedAuthority(user.getAuthority()));
//            user.getAuthorities().stream()
//            .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
        user.getPassword(),
            grantedAuthorities);
    }
}
