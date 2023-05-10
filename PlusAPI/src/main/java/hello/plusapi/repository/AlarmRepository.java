package hello.plusapi.repository;

import hello.plusapi.entity.Alarm;
import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.Users;
import hello.plusapi.enumforentity.AlarmType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    //수정필요!!
    List<Alarm> findBySentenceGroupInAndAlarmType(List<SentenceGroup> sentenceGroupList,
        AlarmType alarmType);

    List<Alarm> findBySubscribedUserAndAlarmType(Users users, AlarmType alarmType);
}
