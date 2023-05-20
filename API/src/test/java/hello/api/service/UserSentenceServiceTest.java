package hello.api.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import hello.api.dto.UserSentenceDto;
import hello.api.dto.UserSentenceKafkaDto;
import hello.api.dto.UserSentenceRequest;
import hello.api.entity.SentenceGroup;
import hello.api.entity.SentenceSubscribe;
import hello.api.entity.UserSentence;
import hello.api.entity.Users;
import hello.api.repository.SentenceGroupRepository;
import hello.api.repository.SentenceSubscribeRepository;
import hello.api.repository.UserRepository;
import hello.api.repository.UserSentenceRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
    SentenceGroupRepository sentenceGroupRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SentenceSubscribeRepository sentenceSubscribeRepository;
    @Autowired
    UserSentenceRepository userSentenceRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void getUserSentenceGroupList() {
        //given
        Users saveUser = userRepository.save(
            new Users(null, "username", "password", "name", null, false));
        SentenceGroup saveGroup1 = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser, null, null));
        SentenceGroup saveGroup2 = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser, null, null));
        SentenceGroup saveGroup3 = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser, null, null));
        entityManager.flush();
        entityManager.clear();
        //when
        List<UserSentenceDto> userSentenceGroupList = userSentenceService.getUserSentenceGroupList(
            UserSentenceRequest.builder().sentenceUserId(
                saveUser.getId()).build());
        //then
        assertThat(userSentenceGroupList).size().isEqualTo(3);
    }

    @Test
    void getUserSentenceSubscribeGroupList() {
        //given
        Users saveUser1 = userRepository.save(
            new Users(null, "username1", "password", "name", null, false));
        Users saveUser2 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        SentenceGroup saveGroup1 = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser1, null, null));
        SentenceGroup saveGroup2 = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser1, null, null));
        SentenceGroup saveGroup3 = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser2, null, null));
        entityManager.flush();
        entityManager.clear();
        //when
        List<UserSentenceDto> userSentenceGroupList =
            userSentenceService.getUserSentenceSubscribeGroupList(
                UserSentenceRequest.builder().sentenceUserId(
                    saveUser2.getId()).build());

        //then
        assertThat(userSentenceGroupList).size().isEqualTo(2);
    }

    @Test
    void getUserSubscribeGroupLIst() {
        //given
        Users saveUser1 = userRepository.save(
            new Users(null, "username1", "password", "name", null, false));
        Users saveUser2 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        SentenceGroup saveGroup3 = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser2, null, null));
        SentenceGroup saveGroup4 = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser2, null, null));
        sentenceSubscribeRepository.save(new SentenceSubscribe(null, saveGroup3, saveUser1));
        sentenceSubscribeRepository.save(new SentenceSubscribe(null, saveGroup4, saveUser1));
        entityManager.flush();
        entityManager.clear();
        //when
        List<UserSentenceDto> userSubscribeGroupLIst = userSentenceService.getUserSubscribeGroupLIst(
            UserSentenceRequest.builder().subscriberId(
                saveUser1.getId()).build());
        //then
        assertThat(userSubscribeGroupLIst).size().isEqualTo(2);
    }

    @Test
    void addSentenceGroup() {
        //given
        Users saveUser1 = userRepository.save(
            new Users(null, "username1", "password", "name", null, false));
        entityManager.flush();
        entityManager.clear();
        //when
        userSentenceService.addSentenceGroup(
            UserSentenceRequest.builder().providerId(saveUser1.getId()).sentenceName("sentenceName")
                .build());
        //then
        List<SentenceGroup> all = sentenceGroupRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void subscribeSentence() {
        //given
        Users saveUser1 = userRepository.save(
            new Users(null, "username1", "password", "name", null, false));
        Users saveUser2 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        SentenceGroup saveGroup = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser2, null, null));
        entityManager.flush();
        entityManager.clear();
        //when
        userSentenceService.subscribeSentence(
            UserSentenceRequest.builder().subscriberId(saveUser1.getId())
                .subscribedSentenceId(saveGroup.getId())
                .build());
        //then
        List<SentenceSubscribe> all = sentenceSubscribeRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void addSentence() {
        //given
        Users saveUser1 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        SentenceGroup saveGroup = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser1, null, null));
        entityManager.flush();
        entityManager.clear();
        //when
        userSentenceService.addSentence(
            UserSentenceRequest.builder().updateSentenceGroupId(saveGroup.getId()).korean("korean")
                .english("english").build());
        //then
        List<UserSentence> all = userSentenceRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void deleteSentence() {
        //given
        Users saveUser1 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        SentenceGroup saveGroup = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser1, null, null));
        SentenceSubscribe saveSubscribe = sentenceSubscribeRepository.save(
            new SentenceSubscribe(null, saveGroup, saveUser1));
        UserSentence save = userSentenceRepository.save(
            new UserSentence(null, saveGroup, "korean", "english"));
        entityManager.flush();
        entityManager.clear();
        //when
        UserSentenceKafkaDto kafkaDto = userSentenceService.deleteSentence(
            UserSentenceRequest.builder().deleteSentenceId(save.getId()).build());
        //then
        List<UserSentence> all = userSentenceRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
        assertThat(kafkaDto.getList().size()).isEqualTo(1);
    }


    @Test
    void getSentenceGroupInfo() {
        //given
        Users saveUser1 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        SentenceGroup saveGroup = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser1, null, null));
        entityManager.flush();
        entityManager.clear();
        //when
        UserSentenceDto sentenceGroupInfo = userSentenceService.getSentenceGroupInfo(
            saveGroup.getId());
        //then
        assertThat(sentenceGroupInfo.getSentenceGroupName()).isEqualTo("name");
    }

    @Test
    void getGroupSentence() {
        //given
        Users saveUser1 = userRepository.save(
            new Users(null, "username2", "password", "name", null, false));
        SentenceGroup saveGroup = sentenceGroupRepository.save(
            new SentenceGroup(null, "name", saveUser1, null, null));
        UserSentence save1 = userSentenceRepository.save(
            new UserSentence(null, saveGroup, "korean", "english"));
        UserSentence save2 = userSentenceRepository.save(
            new UserSentence(null, saveGroup, "korean", "english"));
        entityManager.flush();
        entityManager.clear();
        //when
        List<UserSentenceDto> groupSentence = userSentenceService.getGroupSentence(
            saveGroup.getId());
        //then
        assertThat(groupSentence.size()).isEqualTo(2);
    }
}