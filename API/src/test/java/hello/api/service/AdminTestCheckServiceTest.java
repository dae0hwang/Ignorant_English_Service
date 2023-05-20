package hello.api.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import hello.api.dto.AdminTestCheckRequest;
import hello.api.entity.AdminSentence;
import hello.api.entity.AdminTestCheck;
import hello.api.entity.Users;
import hello.api.enumforentity.Check;
import hello.api.repository.AdminSentenceRepository;
import hello.api.repository.AdminTestCheckRepository;
import hello.api.repository.UserRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AdminTestCheckServiceTest {

    @Autowired
    AdminTestCheckService adminTestCheckService;
    @Autowired
    AdminSentenceRepository adminSentenceRepository;
    @Autowired
    AdminTestCheckRepository adminTestCheckRepository;
    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("이미 값이 존재했을 때 수정되는 테스트")
    void saveOrUpdateAdminTestCheck() {
        //given
        AdminSentence saveAdminSentence = adminSentenceRepository.save(
            new AdminSentence("korean", "english", null, null));
        Users saveUser = userRepository.save(
            new Users(null, "username", "password", "name", null, false));
        AdminTestCheck save = adminTestCheckRepository.save(
            new AdminTestCheck(null, saveAdminSentence, saveUser, Check.CORRECT, null, null));
        entityManager.flush();
        entityManager.clear();
        //when
        adminTestCheckService.saveOrUpdateAdminTestCheck(
            new AdminTestCheckRequest(saveUser.getId(), saveAdminSentence.getId(), "WRONG"));
        //then
        AdminTestCheck find = adminTestCheckRepository.findById(save.getId())
            .orElseThrow();
        assertThat(find.getTestCheck()).isEqualTo(Check.WRONG);
    }

    @Test
    @DisplayName("존재하는 값 없을 때 새로 생성")
    void saveOrUpdateAdminTestCheck2() {
        //given
        AdminSentence saveAdminSentence = adminSentenceRepository.save(
            new AdminSentence("korean", "english", null, null));
        Users saveUser = userRepository.save(
            new Users(null, "username", "password", "name", null, false));
        entityManager.flush();
        entityManager.clear();
        //when
        adminTestCheckService.saveOrUpdateAdminTestCheck(
            new AdminTestCheckRequest(saveUser.getId(), saveAdminSentence.getId(), "WRONG"));
        //then
        AdminTestCheck find = adminTestCheckRepository.findByUsersAndAdminSentence(saveUser,
            saveAdminSentence).orElseThrow();
        assertThat(find.getTestCheck()).isEqualTo(Check.WRONG);
    }


}