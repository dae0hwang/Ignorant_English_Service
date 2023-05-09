package hello.plusapi.repository;

import hello.plusapi.entity.Alarm;
import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    List<Alarm> findBySentenceGroupInOrSubscribedUser(
        List<SentenceGroup> sentenceGroupList, Users users);

    List<Alarm> findBySentenceGroupIn(List<SentenceGroup> sentenceGroupList);

}
