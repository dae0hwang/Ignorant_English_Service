package hello.api.repository;

import hello.api.entity.Alarm;
import hello.api.entity.SentenceGroup;
import hello.api.entity.Users;
import hello.api.enumforentity.AlarmType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    //수정필요!!
    List<Alarm> findBySentenceGroupInAndAlarmType(List<SentenceGroup> sentenceGroupList,
        AlarmType alarmType);

    List<Alarm> findBySubscribedUserAndAlarmType(Users users, AlarmType alarmType);
}
