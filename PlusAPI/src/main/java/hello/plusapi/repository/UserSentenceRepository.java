package hello.plusapi.repository;

import hello.plusapi.entity.UserSentence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSentenceRepository extends JpaRepository<UserSentence, Long> {

}