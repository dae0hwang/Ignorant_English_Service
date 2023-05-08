package hello.plusapi.repository;

import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.UserSentence;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSentenceRepository extends JpaRepository<UserSentence, Long> {

    List<UserSentence> findBySentenceGroup(SentenceGroup sentenceGroup);

}