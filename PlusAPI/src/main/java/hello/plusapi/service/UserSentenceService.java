package hello.plusapi.service;

import static hello.plusapi.enumforkafaka.KafkaTopicEnum.*;

import hello.plusapi.dto.AlarmInfoDto;
import hello.plusapi.dto.UserSentenceDto;
import hello.plusapi.dto.UserSentenceKafkaDto;
import hello.plusapi.dto.UserSentenceRequest;
import hello.plusapi.entity.Alarm;
import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.SentenceSubscribe;
import hello.plusapi.entity.UserSentence;
import hello.plusapi.entity.Users;
import hello.plusapi.enumforentity.AlarmType;
import hello.plusapi.enumforkafaka.KafkaTopicEnum;
import hello.plusapi.enumforkafaka.KafkaTopicEnum.Constants;
import hello.plusapi.repository.AlarmRepository;
import hello.plusapi.repository.SentenceGroupRepository;
import hello.plusapi.repository.SentenceSubscribeRepository;
import hello.plusapi.repository.UserRepository;
import hello.plusapi.repository.UserSentenceRepository;
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
    private final UserSentenceRepository userSentenceRepository;
    private final AlarmRepository alarmRepository;

    //여기서 카프카로 받는다 ㅇㅇ 아니다 이것은 알람 없다 문장 추가나 삭제 있을 때 그런것이다.
    //request대신에 다른 것 진행 ㅇㅇ 아니면 그대로 받아도 되고 ㅇㅇ
    //11 문장 그룹 추가하기.
    //작동 되는 지 확인
    //카프카도 완성
    @Transactional
    @KafkaListener(topics = Constants.CREATETABLE, groupId = "foo", properties = {
        "spring.json.value.default.type:hello.plusapi.dto.UserSentenceKafkaDto"})
    public void addSentenceGroup(UserSentenceKafkaDto kafkaDto) {
        Users findUser = userRepository.findById(kafkaDto.getProviderId()).orElseThrow();
        SentenceGroup sentenceGroup = SentenceGroup.builder().user(findUser)
            .sentenceName(kafkaDto.getSentenceName()).build();
        sentenceGroupRepository.save(sentenceGroup);
        //일단 저장 오케이 그 다음 해야할 것이 알람 서비스 주입 받아서 구독자들에게
    }

    //문장 구독하기 유저 아이디 와 구독할 문장 가져와와서
    //구독하기 여기에는 알람이 추가되어야 한다!!!!!!1
    //22 구독하기
    @Transactional
    @KafkaListener(topics = Constants.SUBSCRIBE, groupId = "foo", properties = {
        "spring.json.value.default.type:hello.plusapi.dto.UserSentenceKafkaDto"})
    public void subscribeSentence(UserSentenceKafkaDto kafkaDto) {
        Users findUser = userRepository.findById(kafkaDto.getSubscriberId()).orElseThrow();

        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            kafkaDto.getSubscribedSentenceId()).orElseThrow();

        SentenceSubscribe sentenceSubscribe = SentenceSubscribe.builder().subscribedUser(findUser)
            .sentenceGroup(findSentenceGroup).build();
        sentenceSubscribeRepository.save(sentenceSubscribe);

        //알림 디비 등록해야한다!!!!!!! 구독자 증가했다고 알림 등록
        String alarmMessage =
            findUser.getName() + "님이 " + findSentenceGroup.getSentenceName() + " 목록을 구독했습니다.";
        Alarm alarm = Alarm.builder().sentenceGroup(findSentenceGroup).subscribedUser(findUser)
            .alarmType(
                AlarmType.SUBSCRIBE).alarmMessage(alarmMessage).build();
        alarmRepository.save(alarm);
    }

    //문장 추가 삭제.
    //이 안에 구독자들 업데이트 버튼도 있다.
    //일단 구독하기부터 만들고 진행하기.
    //request대신에 다른 것 진행 ㅇㅇ 아니면 그대로 받아도 되고 ㅇㅇ

    //33  문장 추가
    @Transactional
    @KafkaListener(topics = Constants.ADDSENTECE, groupId = "foo", properties = {
        "spring.json.value.default.type:hello.plusapi.dto.UserSentenceKafkaDto"})
    public void addSentence(UserSentenceKafkaDto kafkaDto) {
        //등록유저인지

        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            kafkaDto.getUpdateSentenceGroupId()).orElseThrow();
        UserSentence userSentence = UserSentence.builder()
            .sentenceGroup(findSentenceGroup).korean(kafkaDto.getKorean())
            .english(kafkaDto.getEnglish()).build();
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
                    AlarmType.UPDATE).alarmMessage(alarmMessage).build();
            alarmRepository.save(alarm);
        }
    }

    //44  문장 삭제
    //이것은 일단 문장 추가부터 성공하면 진행하기. ㅇㅇㅇㅇ
    @Transactional
    @KafkaListener(topics = Constants.DELETESENTENCE, groupId = "foo", properties = {
        "spring.json.value.default.type:hello.plusapi.dto.UserSentenceKafkaDto"})
    public void deleteSentence(UserSentenceKafkaDto kafkaDto) {
        UserSentence findUserSentence = userSentenceRepository.findById(
                kafkaDto.getDeleteSentenceId())
            .orElseThrow();
        //지울 문장 아이디로 지우고 찾은 문장의
        userSentenceRepository.deleteById(kafkaDto.getDeleteSentenceId());


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
                    AlarmType.UPDATE).alarmMessage(alarmMessage).build();
            alarmRepository.save(alarm);
        }
    }

    //55  알림 확인하기
    @Transactional
    @KafkaListener(topics = Constants.CHECKALARM, groupId = "foo", properties = {
        "spring.json.value.default.type:hello.plusapi.dto.UserSentenceKafkaDto"})
    public void checkAlarm(UserSentenceKafkaDto kafkaDto) {
        //해당 그냥 삭제하기
        alarmRepository.deleteById(kafkaDto.getAlarmId());
    }



    //ui랑 직접 소통
    public List<UserSentenceDto> getUserSentenceGroupList(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getSentenceUserId()).orElseThrow();
        List<SentenceGroup> findGroupList = sentenceGroupRepository.findListByUser(findUser);
        List<UserSentenceDto> list = new ArrayList<>();
        for (SentenceGroup sentenceGroup : findGroupList) {
            list.add(
                UserSentenceDto.builder().sentenceGroupId(sentenceGroup.getId()).sentenceGroupName(
                    sentenceGroup.getSentenceName()).build());
        }
        return list;
    }

    public UserSentenceDto getSentenceGroupInfo(Long groupId) {
        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(groupId).orElseThrow();
        UserSentenceDto dto = UserSentenceDto.builder()
            .sentenceGroupName(findSentenceGroup.getSentenceName()).build();
        return dto;
    }

    public List<UserSentenceDto> getGroupSentence(Long groupId) {
        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(groupId).orElseThrow();
        List<UserSentence> findUserSentence = userSentenceRepository.findBySentenceGroup(
            findSentenceGroup);
        List<UserSentenceDto> list = new ArrayList<>();
        for (UserSentence userSentence : findUserSentence) {
            list.add(UserSentenceDto.builder().sentenceId(userSentence.getId()).korean(
                userSentence.getKorean()).english(userSentence.getEnglish()).build());
        }
        return list;
    }

    public List<UserSentenceDto> getUserSentenceSubscribeGroupList(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getSentenceUserId()).orElseThrow();

        List<SentenceGroup> findGroupList = sentenceGroupRepository.findListByUserNot(findUser);
        List<UserSentenceDto> list = new ArrayList<>();
        for (SentenceGroup sentenceGroup : findGroupList) {
            list.add(
                UserSentenceDto.builder().sentenceGroupId(sentenceGroup.getId()).sentenceGroupName(
                    sentenceGroup.getSentenceName()).build());
        }
        return list;
    }


    public List<UserSentenceDto> getUserSubscribeGroupLIst(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getSubscriberId()).orElseThrow();
        List<SentenceSubscribe> findGroupList = sentenceSubscribeRepository.findListBySubscribedUser(
            findUser);
        List<UserSentenceDto> list = new ArrayList<>();
        for (SentenceSubscribe sentenceSubscribe : findGroupList) {
            SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
                sentenceSubscribe.getSentenceGroup().getId()).orElseThrow();
            list.add(
                UserSentenceDto.builder().sentenceGroupId(findSentenceGroup.getId()).sentenceGroupName(
                    findSentenceGroup.getSentenceName()).build());
        }
        return list;

    }


    //나와 관련된 알람 전부다 가져오기
    public List<AlarmInfoDto> getMyAlarmList(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getAlarmUserId()).orElseThrow();
        List<SentenceGroup> groupList = sentenceGroupRepository.findListByUser(findUser);
        List<Alarm> alarmList = alarmRepository.findBySentenceGroupInOrSubscribedUser(groupList,
            findUser);
        List<AlarmInfoDto> list = new ArrayList<>();
        for (Alarm alarm : alarmList) {
            list.add(AlarmInfoDto.builder().alarmId(alarm.getId()).alarmMessage(
                alarm.getAlarmMessage()).alarmType(alarm.getAlarmType().getStringCheck()).build());
        }
        return list;
    }


    //알람 삭제하기
    public void deleteAlarm(UserSentenceRequest request) {
        alarmRepository.deleteById(request.getAlarmId());
    }
}
