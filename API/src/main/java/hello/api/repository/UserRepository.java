package hello.api.repository;

import hello.api.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Optional<Users> findByUsernameAndEmailAuthFalse(String username);

    @Override
    void delete(Users users);
}