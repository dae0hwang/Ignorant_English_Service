package hello.api.repository;

import hello.api.entity.SentenceGroup;
import hello.api.entity.SentenceSubscribe;
import hello.api.entity.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceSubscribeRepository extends JpaRepository<SentenceSubscribe, Long> {

    List<SentenceSubscribe> findListBySubscribedUser(Users users);
    List<SentenceSubscribe> findListBySentenceGroup(SentenceGroup sentenceGroup);
    Optional<SentenceSubscribe> findBySubscribedUserAndSentenceGroup(Users users,
        SentenceGroup sentenceGroup);
}
