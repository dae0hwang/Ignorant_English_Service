package hello.plusapi.service;

import hello.plusapi.dto.UserSentenceRequest;
import hello.plusapi.entity.Alarm;
import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.SentenceSubscribe;
import hello.plusapi.entity.UserSentence;
import hello.plusapi.entity.Users;
import hello.plusapi.enumforentity.AlarmType;
import hello.plusapi.repository.AlarmRepository;
import hello.plusapi.repository.SentenceGroupRepository;
import hello.plusapi.repository.SentenceSubscribeRepository;
import hello.plusapi.repository.UserRepository;
import hello.plusapi.repository.UserSentenceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSentenceService {

    private final SentenceGroupRepository sentenceGroupRepository;
    private final UserRepository userRepository;
    private final SentenceSubscribeRepository sentenceSubscribeRepository;
    private final UserSentenceRepository userSentenceRepository;
    private final AlarmRepository alarmRepository;

    //여기서 카프카로 받는다 ㅇㅇ 아니다 이것은 알람 없다 문장 추가나 삭제 있을 때 그런것이다.
    //request대신에 다른 것 진행 ㅇㅇ 아니면 그대로 받아도 되고 ㅇㅇ
    //11 문장 그룹 추가하기.
    //작동 되는 지 확인
    @Transactional
    public void addSentenceGroup(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getProviderId()).orElseThrow();
        SentenceGroup sentenceGroup = SentenceGroup.builder().user(findUser)
            .sentenceName(request.getSentenceName()).build();
        sentenceGroupRepository.save(sentenceGroup);
        //일단 저장 오케이 그 다음 해야할 것이 알람 서비스 주입 받아서 구독자들에게
    }

    //문장 구독하기 유저 아이디 와 구독할 문장 가져와와서
    //구독하기 여기에는 알람이 추가되어야 한다!!!!!!1
    //22 구독하기
    @Transactional
    public void subscribeSentence(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getSubscriberId()).orElseThrow();

        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            request.getSubscribedSentenceId()).orElseThrow();

        SentenceSubscribe sentenceSubscribe = SentenceSubscribe.builder().subscribedUser(findUser)
            .sentenceGroup(findSentenceGroup).build();
        sentenceSubscribeRepository.save(sentenceSubscribe);

        //알림 디비 등록해야한다!!!!!!! 구독자 증가했다고 알림 등록
        String alarmMessage =
            findUser.getName() + "님이 " + findSentenceGroup.getSentenceName() + " 목록을 구독했습니다.";
        Alarm alarm = Alarm.builder().sentenceGroup(findSentenceGroup).subscribedUser(findUser)
            .alarmType(
                AlarmType.SUBSCRIBE).alarmMessage(alarmMessage).checkStatus(false).build();
        alarmRepository.save(alarm);
    }

    //문장 추가 삭제.
    //이 안에 구독자들 업데이트 버튼도 있다.
    //일단 구독하기부터 만들고 진행하기.
    //request대신에 다른 것 진행 ㅇㅇ 아니면 그대로 받아도 되고 ㅇㅇ

    //33  문장 추가
    @Transactional
    public void addSentence(UserSentenceRequest request) {
        //등록유저인지

        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            request.getUpdateSentenceGroupId()).orElseThrow();
        UserSentence userSentence = UserSentence.builder()
            .sentenceGroup(findSentenceGroup).korean(request.getKorean())
            .english(request.getEnglish()).build();
        userSentenceRepository.save(userSentence);

        //알림 가야한다.!!!!!!!!
        List<SentenceSubscribe> findSubuscribeList = sentenceSubscribeRepository
            .findListBySentenceGroup(findSentenceGroup);
        String alarmMessage =
            findSentenceGroup.getSentenceName() + " 목록에 문장이 추가되었습니다.";
        System.out.println("findList= "+findSubuscribeList.toString());
        for (SentenceSubscribe sentenceSubscribe : findSubuscribeList) {
            System.out.println("들어옴  22 1212 ");
            Alarm alarm = Alarm.builder().sentenceGroup(findSentenceGroup)
                .subscribedUser(sentenceSubscribe.getSubscribedUser())
                .alarmType(
                    AlarmType.UPDATE).alarmMessage(alarmMessage).checkStatus(false).build();
            alarmRepository.save(alarm);
        }
    }

    //44  문장 삭제
    //이것은 일단 문장 추가부터 성공하면 진행하기. ㅇㅇㅇㅇ
    @Transactional
    public void deleteSentence(UserSentenceRequest request) {
        UserSentence findUserSentence = userSentenceRepository.findById(
                request.getDeleteSentenceId())
            .orElseThrow();
        //지울 문장 아이디로 지우고 찾은 문장의
        userSentenceRepository.deleteById(request.getDeleteSentenceId());


        //request안에는 해당 테이블 목록도 가지고 있어야 한다.
        //알림 가야한다.!!!!!!!!
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
                    AlarmType.UPDATE).alarmMessage(alarmMessage).checkStatus(false).build();
            alarmRepository.save(alarm);
        }
    }

    //55  알림 확인하기
    @Transactional
    public void checkAlarm(UserSentenceRequest request) {
        //해당 알림이 무엇인지 들고와야지 alarm
        Alarm findAlarm = alarmRepository.findById(request.getAlarmId()).orElseThrow();
        findAlarm.setCheckStatus(true);
    }
}
