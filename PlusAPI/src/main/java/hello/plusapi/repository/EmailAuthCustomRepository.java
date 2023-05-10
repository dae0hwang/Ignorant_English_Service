package hello.plusapi.repository;

import hello.plusapi.entity.EmailAuth;
import java.util.Optional;

public interface EmailAuthCustomRepository {
    Optional<EmailAuth> findValidAuthByEmail(String authToken);

    Optional<EmailAuth> findExpiredAuth(String authToken);
}
