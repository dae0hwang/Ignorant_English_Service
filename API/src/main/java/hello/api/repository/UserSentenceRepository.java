package hello.api.repository;

import hello.api.entity.SentenceGroup;
import hello.api.entity.UserSentence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSentenceRepository extends JpaRepository<UserSentence, Long> {

    List<UserSentence> findBySentenceGroup(SentenceGroup sentenceGroup);
}