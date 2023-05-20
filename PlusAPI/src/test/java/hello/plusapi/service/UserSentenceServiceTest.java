package hello.plusapi.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import hello.plusapi.dto.AlarmInfoDto;
import hello.plusapi.dto.UserSentenceKafkaDto;
import hello.plusapi.dto.UserSentenceRequest;
import hello.plusapi.entity.Alarm;
import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.SentenceSubscribe;
import hello.plusapi.entity.Users;
import hello.plusapi.enumforentity.AlarmType;
import hello.plusapi.repository.AlarmRepository;
import hello.plusapi.repository.SentenceGroupRepository;
import hello.plusapi.repository.SentenceSubscribeRepository;
import hello.plusapi.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.tomcat.jni.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class UserSentenceServiceTest {

    @Autowired
    UserSentenceService userSentenceService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SentenceGroupRepository sentenceGroupRepository;
    @Autowired
    AlarmRepository alarmRepository;
    @Autowired
    SentenceSubscribeRepository sentenceSubscribeRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void getMyAlarmList() {
        //given
        Users sentenceUser = userRepository.save(
            new Users(null, "username1", "password", "name", null, false));
        Users subScribeUser1 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        Users subScribeUser2 = userRepository.save(
            new Users(null, "username3", "password", "name", null, false));
        SentenceGroup sentenceGroup1 = sentenceGroupRepository.save(
            new SentenceGroup(null, "sentenceName", sentenceUser, null, null));
        alarmRepository.save(
            new Alarm(null, sentenceGroup1, subScribeUser1, AlarmType.UPDATE, null));
        alarmRepository.save(
            new Alarm(null, sentenceGroup1, subScribeUser1, AlarmType.SUBSCRIBE, null));
        alarmRepository.save(
            new Alarm(null, sentenceGroup1, subScribeUser2, AlarmType.SUBSCRIBE, null));
        entityManager.flush();
        entityManager.clear();
        //when
        List<AlarmInfoDto> myAlarmList1 = userSentenceService.getMyAlarmList(
            UserSentenceRequest.builder().alarmUserId(subScribeUser1.getId()).build());
        List<AlarmInfoDto> myAlarmList2 = userSentenceService.getMyAlarmList(
            UserSentenceRequest.builder().alarmUserId(sentenceUser.getId()).build());
        //then
        assertThat(myAlarmList1.size()).isEqualTo(1);
        assertThat(myAlarmList2.size()).isEqualTo(2);
    }

    @Test
    void deleteAlarm() {
        //given
        Users sentenceUser = userRepository.save(
            new Users(null, "username1", "password", "name", null, false));
        Users subScribeUser1 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        SentenceGroup sentenceGroup1 = sentenceGroupRepository.save(
            new SentenceGroup(null, "sentenceName", sentenceUser, null, null));
        Alarm save = alarmRepository.save(
            new Alarm(null, sentenceGroup1, subScribeUser1, AlarmType.UPDATE, null));
        entityManager.flush();
        entityManager.clear();
        //when
        userSentenceService.deleteAlarm(
            UserSentenceRequest.builder().alarmId(save.getId()).build());
        //then
        List<Alarm> all = alarmRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    void subscribeSentence() {
        //given
        Users sentenceUser = userRepository.save(
            new Users(null, "username1", "password", "name", null, false));
        Users subScribeUser1 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        SentenceGroup sentenceGroup1 = sentenceGroupRepository.save(
            new SentenceGroup(null, "sentenceName", sentenceUser, null, null));
        entityManager.flush();
        entityManager.clear();
        //when
        userSentenceService.subscribeSentence(UserSentenceKafkaDto.builder().subscriberId(
            subScribeUser1.getId()).subscribedSentenceId(sentenceGroup1.getId()).build());
        //then
        List<Alarm> all = alarmRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void addSentence() {
        //given
        SentenceGroup sentenceGroup = sentenceGroupRepository.save(
            new SentenceGroup(null, "sentenceName", null, null, null));
        Users subscriber1 = userRepository.save(
            new Users(null, "username", "password", "name", null, null));
        Users subscriber2 = userRepository.save(
            new Users(null, "username", "password", "name", null, null));
        sentenceSubscribeRepository.save(new SentenceSubscribe(null, sentenceGroup, subscriber1));
        sentenceSubscribeRepository.save(new SentenceSubscribe(null, sentenceGroup, subscriber2));
        entityManager.flush();
        entityManager.clear();
        //when
        userSentenceService.addSentence(
            UserSentenceKafkaDto.builder().updateSentenceGroupId(sentenceGroup.getId()).build());
        //then
        List<Alarm> all = alarmRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }

    @Test
    void deleteSentence() {
        //given
        SentenceGroup sentenceGroup = sentenceGroupRepository.save(
            new SentenceGroup(null, "sentenceName", null, null, null));
        Users subscriber1 = userRepository.save(
            new Users(null, "username", "password", "name", null, null));
        Users subscriber2 = userRepository.save(
            new Users(null, "username", "password", "name", null, null));
        sentenceSubscribeRepository.save(new SentenceSubscribe(null, sentenceGroup, subscriber1));
        sentenceSubscribeRepository.save(new SentenceSubscribe(null, sentenceGroup, subscriber2));
        entityManager.flush();
        entityManager.clear();
        //when
        List<SentenceSubscribe> build = List.of(
            SentenceSubscribe.builder().sentenceGroup(sentenceGroup).subscribedUser(subscriber1)
                .build(),
            SentenceSubscribe.builder().sentenceGroup(sentenceGroup).subscribedUser(subscriber2)
                .build());
        userSentenceService.deleteSentence(
            UserSentenceKafkaDto.builder().list(build).sentenceGroup(sentenceGroup).build());
        //then
        List<Alarm> all = alarmRepository.findAll();
        assertThat(all.size()).isEqualTo(2);
    }
}