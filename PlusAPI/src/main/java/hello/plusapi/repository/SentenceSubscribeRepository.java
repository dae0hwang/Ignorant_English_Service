package hello.plusapi.repository;

import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.SentenceSubscribe;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceSubscribeRepository extends JpaRepository<SentenceSubscribe, Long> {

    List<SentenceSubscribe> findListBySentenceGroup(SentenceGroup sentenceGroup);
}
