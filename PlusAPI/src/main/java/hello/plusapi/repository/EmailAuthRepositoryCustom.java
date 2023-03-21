package hello.plusapi.repository;

import hello.plusapi.Entity.EmailAuth;
import java.sql.Timestamp;
import java.util.Optional;

public interface EmailAuthRepositoryCustom {
    Optional<EmailAuth> findValidAuthByEmail(String authToken, Timestamp currentTime);
}
