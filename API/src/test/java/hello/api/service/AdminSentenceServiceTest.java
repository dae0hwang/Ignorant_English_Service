package hello.api.service;

import hello.api.dto.AdminSentenceDto;
import hello.api.dto.AdminTestListConditionRequest;
import hello.api.dto.AdminTestSentenceDto;
import hello.api.entity.AdminSentence;
import hello.api.entity.AdminTestCheck;
import hello.api.entity.Users;
import hello.api.enumforentity.Check;
import hello.api.enumforentity.Grammar;
import hello.api.enumforentity.Situation;
import hello.api.exception.AdminSentenceException;
import hello.api.repository.AdminSentenceRepository;
import hello.api.repository.AdminTestCheckRepository;
import hello.api.repository.UserRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AdminSentenceServiceTest {

    @Autowired
    AdminSentenceService adminSentenceService;
    @Autowired
    AdminSentenceRepository adminSentenceRepository;
    @Autowired
    AdminTestCheckRepository adminTestCheckRepository;
    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void saveSentence() {
        //given
        //when
        adminSentenceService.saveSentence("korean", "english", "IF", "NO");
        //then
        List<AdminSentence> all = adminSentenceRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void saveSentenceException1() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(
                () -> adminSentenceService.saveSentence("korean", "english", "no-match", "NO"))
            .isInstanceOf(AdminSentenceException.class);
    }

    @Test
    void saveSentenceException2() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(
                () -> adminSentenceService.saveSentence("korean", "english", "NO", "no-match"))
            .isInstanceOf(AdminSentenceException.class);
    }

    @Test
    void findGrammarValues() {
        //given
        //when
        List<String> grammarValues = adminSentenceService.findGrammarValues();
        //then
        Assertions.assertThat(grammarValues.size()).isEqualTo(Grammar.values().length);
    }

    @Test
    void findSituationValues() {
        //given
        //when
        List<String> situationValues = adminSentenceService.findSituationValues();
        //then
        Assertions.assertThat(situationValues.size()).isEqualTo(Situation.values().length);
    }

    @Test
    void findAll() {
        //given
        AdminSentence save1 = adminSentenceRepository.save(
            new AdminSentence("korean1", "english1", Grammar.IF, Situation.NO));
        AdminSentence save2 = adminSentenceRepository.save(
            new AdminSentence("korean2", "english2", Grammar.NO, Situation.BUSINESS));
        //when
        List<AdminSentenceDto> all = adminSentenceService.findAll();
        //then
        Assertions.assertThat(all).containsExactly(
            new AdminSentenceDto(save1.getId(), save1.getKorean(), save1.getEnglish(),
                save1.getGrammar().getStringGrammar(), save1.getSituation().getStringSituation()),
            new AdminSentenceDto(save2.getId(), save2.getKorean(), save2.getEnglish(),
                save2.getGrammar().getStringGrammar(), save2.getSituation().getStringSituation()));
    }

    @Test
    void delete() {
        //given
        AdminSentence save = adminSentenceRepository.save(
            new AdminSentence("korean1", "english1", Grammar.IF, Situation.NO));
        //when
        adminSentenceService.delete(save.getId());
        //then
        List<AdminSentenceDto> all = adminSentenceService.findAll();
        Assertions.assertThat(all).isEmpty();
    }

    @Test
    void findById() {
        //given
        AdminSentence save = adminSentenceRepository.save(
            new AdminSentence("korean1", "english1", Grammar.IF, Situation.NO));
        AdminSentenceDto actual = new AdminSentenceDto(save.getId(), save.getKorean(),
            save.getEnglish(), save.getGrammar().getStringGrammar(),
            save.getSituation().getStringSituation());
        //when
        AdminSentenceDto findById = adminSentenceService.findById(save.getId());
        //then
        Assertions.assertThat(findById).isEqualTo(actual);
    }

    @Test
    void update() {
        //given
        AdminSentence save = adminSentenceRepository.save(
            new AdminSentence("korean1", "english1", Grammar.IF, Situation.NO));
        //when
        adminSentenceService.update(save.getId(), "korean1", "change", "NO", "NO");
        //then
        AdminSentence changedEntity = adminSentenceRepository.findById(save.getId())
            .orElseThrow();
        Assertions.assertThat(changedEntity.getKorean()).isEqualTo("korean1");
        Assertions.assertThat(changedEntity.getEnglish()).isEqualTo("change");
        Assertions.assertThat(changedEntity.getGrammar()).isEqualTo(Grammar.NO);
        Assertions.assertThat(changedEntity.getSituation()).isEqualTo(Situation.NO);
    }

    @Test
    void findAdminTestListByCondition() {
        //given
        Users saveUser = userRepository.save(
            new Users(null, "username", "password", "name", null, false));
        AdminSentence save1 = adminSentenceRepository.save(
            new AdminSentence("korean1", "english1", Grammar.IF, Situation.NO));
        AdminSentence save2 =adminSentenceRepository.save(
            new AdminSentence("korean2", "english2", Grammar.HAVEPP, Situation.NO));
        AdminSentence save3 =adminSentenceRepository.save(
            new AdminSentence("korean3", "english3", Grammar.NO, Situation.NO));

        adminTestCheckRepository.save(
            new AdminTestCheck(null, save1, saveUser, Check.CORRECT, null, null));
        adminTestCheckRepository.save(
            new AdminTestCheck(null, save2, saveUser, Check.WRONG, null, null));
        adminTestCheckRepository.save(
            new AdminTestCheck(null, save3, saveUser, Check.WRONG, null, null));
        entityManager.flush();
        entityManager.clear();
        //when
        List<AdminTestSentenceDto> adminTestListByCondition = adminSentenceService.findAdminTestListByCondition(
            new AdminTestListConditionRequest(saveUser.getId(), "NO", "NO", "CORRECT"));
        //then
        Assertions.assertThat(adminTestListByCondition.size()).isEqualTo(1);
    }
}