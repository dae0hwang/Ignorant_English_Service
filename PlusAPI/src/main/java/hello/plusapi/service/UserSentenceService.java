package hello.plusapi.service;

import hello.plusapi.dto.AlarmInfoDto;
import hello.plusapi.dto.UserSentenceKafkaDto;
import hello.plusapi.dto.UserSentenceRequest;
import hello.plusapi.entity.Alarm;
import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.SentenceSubscribe;
import hello.plusapi.entity.Users;
import hello.plusapi.enumforentity.AlarmType;
import hello.plusapi.enumforkafaka.KafkaTopicEnum.Constants;
import hello.plusapi.repository.AlarmRepository;
import hello.plusapi.repository.SentenceGroupRepository;
import hello.plusapi.repository.SentenceSubscribeRepository;
import hello.plusapi.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSentenceService {

    private final SentenceGroupRepository sentenceGroupRepository;
    private final UserRepository userRepository;
    private final SentenceSubscribeRepository sentenceSubscribeRepository;
    private final AlarmRepository alarmRepository;

    //나와 관련된 알람 전부다 가져오기
    public List<AlarmInfoDto> getMyAlarmList(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getAlarmUserId()).orElseThrow();
        List<SentenceGroup> groupList = sentenceGroupRepository.findListByUser(findUser);
        for (SentenceGroup sentenceGroup : groupList) {
            System.out.println("group== "+sentenceGroup);
        }

        List<Alarm> subscribeAlarmList = alarmRepository.findBySentenceGroupInAndAlarmType(
            groupList, AlarmType.SUBSCRIBE);
        List<Alarm> updateAlarmList = alarmRepository.findBySubscribedUserAndAlarmType(
            findUser, AlarmType.UPDATE);
        subscribeAlarmList.addAll(updateAlarmList);

        List<AlarmInfoDto> list = new ArrayList<>();
        for (Alarm alarm : subscribeAlarmList) {
            list.add(AlarmInfoDto.builder().alarmId(alarm.getId()).alarmMessage(
                alarm.getAlarmMessage()).alarmType(alarm.getAlarmType().getStringCheck()).build());
        }
        return list;
    }

    //알람 삭제하기
    public void deleteAlarm(UserSentenceRequest request) {
        alarmRepository.deleteById(request.getAlarmId());
    }

    //Kafka1. 문장 구독하기 알림 보내기
    @Transactional
    @KafkaListener(topics = Constants.SUBSCRIBE, groupId = "foo", properties = {
        "spring.json.value.default.type:hello.plusapi.dto.UserSentenceKafkaDto"})
    public void subscribeSentence(UserSentenceKafkaDto kafkaDto) {
        Users findUser = userRepository.findById(kafkaDto.getSubscriberId()).orElseThrow();
        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            kafkaDto.getSubscribedSentenceId()).orElseThrow();
        String alarmMessage =
            findUser.getName() + "님이 " + findSentenceGroup.getSentenceName() + " 목록을 구독했습니다.";
        Alarm alarm = Alarm.builder().sentenceGroup(findSentenceGroup).subscribedUser(findUser)
            .alarmType(
                AlarmType.SUBSCRIBE).alarmMessage(alarmMessage).build();
        alarmRepository.save(alarm);
    }

    //Kafka 2. 문장 추가하기
    @Transactional
    @KafkaListener(topics = Constants.ADDSENTECE, groupId = "foo", properties = {
        "spring.json.value.default.type:hello.plusapi.dto.UserSentenceKafkaDto"})
    public void addSentence(UserSentenceKafkaDto kafkaDto) {
        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            kafkaDto.getUpdateSentenceGroupId()).orElseThrow();
        List<SentenceSubscribe> findSubuscribeList = sentenceSubscribeRepository
            .findListBySentenceGroup(findSentenceGroup);
        String alarmMessage =
            findSentenceGroup.getSentenceName() + " 목록에 문장이 추가되었습니다.";
        System.out.println("findList= "+findSubuscribeList.toString());
        for (SentenceSubscribe sentenceSubscribe : findSubuscribeList) {
            Alarm alarm = Alarm.builder().sentenceGroup(findSentenceGroup)
                .subscribedUser(sentenceSubscribe.getSubscribedUser())
                .alarmType(
                    AlarmType.UPDATE).alarmMessage(alarmMessage).build();
            alarmRepository.save(alarm);
        }
    }

    //Kafka 3. 문장 삭제하기
    @Transactional
    @KafkaListener(topics = Constants.DELETESENTENCE, groupId = "foo", properties = {
        "spring.json.value.default.type:hello.plusapi.dto.UserSentenceKafkaDto"})
    public void deleteSentence(UserSentenceKafkaDto kafkaDto) {
        String alarmMessage =
            kafkaDto.getSentenceGroup().getSentenceName() + " 목록에 문장이 삭제 되었습니다.";
        for (SentenceSubscribe sentenceSubscribe : kafkaDto.getList()) {
            Alarm alarm = Alarm.builder().sentenceGroup(kafkaDto.getSentenceGroup())
                .subscribedUser(sentenceSubscribe.getSubscribedUser())
                .alarmType(
                    AlarmType.UPDATE).alarmMessage(alarmMessage).build();
            alarmRepository.save(alarm);
        }
    }
}
