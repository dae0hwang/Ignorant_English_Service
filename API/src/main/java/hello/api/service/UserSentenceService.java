package hello.api.service;

import static hello.api.enumforexception.UserManageExceptionEnum.*;

import hello.api.dto.UserSentenceDto;
import hello.api.dto.UserSentenceRequest;
import hello.api.entity.SentenceGroup;
import hello.api.entity.SentenceSubscribe;
import hello.api.entity.UserSentence;
import hello.api.entity.Users;
import hello.api.exception.DuplicatedSubscribeException;
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
public class UserSentenceService {

    private final SentenceGroupRepository sentenceGroupRepository;
    private final UserRepository userRepository;
    private final SentenceSubscribeRepository sentenceSubscribeRepository;
    private final UserSentenceRepository userSentenceRepository;
    private final SentenceAlarmService sentenceAlarmService;


    //유저가 관리하는 문장 가져오기
    @Transactional
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

    //앞으로 구독할 문장 그룹 리스트 가져오기(자신이 만든 문자 제외)
    @Transactional
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

    //유저가 구독한 문장 그룹 리스트 가져오기
    @Transactional
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

    //새로운 문장 그룹 추가하기
    @Transactional
    public void addSentenceGroup(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getProviderId()).orElseThrow();
        SentenceGroup sentenceGroup = SentenceGroup.builder().user(findUser)
            .sentenceName(request.getSentenceName()).build();
        sentenceGroupRepository.save(sentenceGroup);
    }

    //다른 유저 문장 구독하기
    @Transactional
    public void subscribeSentence(UserSentenceRequest request) {
        Users findUser = userRepository.findById(request.getSubscriberId()).orElseThrow();
        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            request.getSubscribedSentenceId()).orElseThrow();
        checkedDuplicatedSubscribeGroup(findUser, findSentenceGroup);
        SentenceSubscribe sentenceSubscribe = SentenceSubscribe.builder().subscribedUser(findUser)
            .sentenceGroup(findSentenceGroup).build();
        sentenceSubscribeRepository.save(sentenceSubscribe);
    }

    //자신 문장 그룹 문장 추가하기
    @Transactional
    public void addSentence(UserSentenceRequest request) {
        SentenceGroup findSentenceGroup = sentenceGroupRepository.findById(
            request.getUpdateSentenceGroupId()).orElseThrow();
        UserSentence userSentence = UserSentence.builder()
            .sentenceGroup(findSentenceGroup).korean(request.getKorean())
            .english(request.getEnglish()).build();
        userSentenceRepository.save(userSentence);
    }

    //자신 문장 그룹 문장 삭제하기
    @Transactional
    public void deleteSentence(UserSentenceRequest request) {
        sentenceAlarmService.deleteSentence(request.getDeleteSentenceId());
        userSentenceRepository.deleteById(request.getDeleteSentenceId());
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
    private void checkedDuplicatedSubscribeGroup(Users users, SentenceGroup sentenceGroup) {
        sentenceSubscribeRepository.findBySubscribedUserAndSentenceGroup(users, sentenceGroup)
            .ifPresent(m -> {
                throw new DuplicatedSubscribeException(DUPLICATED_SUBSCRIBE.getErrormessage());
            });
    }
}
