package hello.api.service;

import hello.api.dto.AlarmInfoDto;
import hello.api.dto.UserSentenceRequest;
import hello.api.entity.Alarm;
import hello.api.entity.SentenceGroup;
import hello.api.entity.SentenceSubscribe;
import hello.api.entity.UserSentence;
import hello.api.entity.Users;
import hello.api.enumforentity.AlarmType;
import hello.api.repository.AlarmRepository;
import hello.api.repository.SentenceGroupRepository;
import hello.api.repository.SentenceSubscribeRepository;
import hello.api.repository.UserRepository;
import hello.api.repository.UserSentenceRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SentenceAlarmService {
    private final SentenceGroupRepository sentenceGroupRepository;
    private final UserRepository userRepository;
    private final SentenceSubscribeRepository sentenceSubscribeRepository;
    private final AlarmRepository alarmRepository;
    private final UserSentenceRepository userSentenceRepository;


    //나와 관련된 알람 전부다 가져오기
    public List<AlarmInfoDto> getMyAlarmList(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getAlarmUserId()).orElseThrow();
        List<SentenceGroup> groupList = sentenceGroupRepository.findListByUser(findUser);

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

    @Transactional
    public void subscribeSentence(Long subscriberId, Long subscribedSentenceId) {
        Users findUser = userRepository.findById(subscriberId).orElseThrow();
        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            subscribedSentenceId).orElseThrow();
        String alarmMessage =
            findUser.getName() + "님이 " + findSentenceGroup.getSentenceName() + " 목록을 구독했습니다.";
        Alarm alarm = Alarm.builder().sentenceGroup(findSentenceGroup).subscribedUser(findUser)
            .alarmType(
                AlarmType.SUBSCRIBE).alarmMessage(alarmMessage).build();
        alarmRepository.save(alarm);
    }

    @Transactional
    public void addSentence(Long updateSentenceGroupId) {
        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            updateSentenceGroupId).orElseThrow();
        List<SentenceSubscribe> findSubuscribeList = sentenceSubscribeRepository
            .findListBySentenceGroup(findSentenceGroup);
        String alarmMessage =
            findSentenceGroup.getSentenceName() + " 목록에 문장이 추가되었습니다.";
        System.out.println("findList= " + findSubuscribeList.toString());
        for (SentenceSubscribe sentenceSubscribe : findSubuscribeList) {
            Alarm alarm = Alarm.builder().sentenceGroup(findSentenceGroup)
                .subscribedUser(sentenceSubscribe.getSubscribedUser())
                .alarmType(
                    AlarmType.UPDATE).alarmMessage(alarmMessage).build();
            alarmRepository.save(alarm);
        }
    }

    @Transactional
    public void deleteSentence(Long deleteSentenceId) {
        UserSentence findUserSentence = userSentenceRepository.findById(deleteSentenceId)
            .orElseThrow();
        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            findUserSentence.getSentenceGroup().getId()).orElseThrow();
        List<SentenceSubscribe> findSubuscribeList = sentenceSubscribeRepository
            .findListBySentenceGroup(findSentenceGroup);
        String alarmMessage =
            findSentenceGroup.getSentenceName() + " 목록에 문장이 삭제 되었습니다.";
        for (SentenceSubscribe sentenceSubscribe : findSubuscribeList) {
            Alarm alarm = Alarm.builder().sentenceGroup(findSentenceGroup)
                .subscribedUser(sentenceSubscribe.getSubscribedUser())
                .alarmType(
                    AlarmType.UPDATE).alarmMessage(alarmMessage).build();
            alarmRepository.save(alarm);
        }
    }
}
