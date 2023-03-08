package hello.api.repository;

import static org.assertj.core.api.Assertions.assertThat;

import hello.api.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    void findOneWithAuthoritiesByEmail() {
        //given
        Users saveUser = userRepository.save(
            Users.builder().email("email@naver.com").password("password").name("name").authority(
                "ROLE_USER").build());
        //when
        Users findUser = userRepository.findByEmail("email@naver.com").get();
        //then
        assertThat(findUser).isEqualTo(saveUser);
    }
}