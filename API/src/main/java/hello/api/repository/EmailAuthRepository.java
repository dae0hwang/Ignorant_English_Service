package hello.api.repository;

import hello.api.entity.EmailAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    Optional<EmailAuth> findByEmailAndExpiredTrue(String email);
}
