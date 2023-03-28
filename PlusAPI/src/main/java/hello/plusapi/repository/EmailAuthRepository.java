package hello.plusapi.repository;

import hello.plusapi.entity.EmailAuth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long>, EmailAuthCustomRepository {

    Optional<EmailAuth> findByEmail(String email);
}
