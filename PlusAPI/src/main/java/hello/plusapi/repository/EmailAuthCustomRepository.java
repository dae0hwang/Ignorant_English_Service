package hello.plusapi.repository;

import hello.plusapi.entity.EmailAuth;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthCustomRepository {
    Optional<EmailAuth> findValidAuthByEmail(String authToken, LocalDateTime currentTime);

    Optional<EmailAuth> findExpiredAuth(String authToken, LocalDateTime currentTime);
}
