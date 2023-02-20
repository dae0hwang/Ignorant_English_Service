package hello.api.service;

import hello.api.dto.AdminSentenceDto;
import hello.api.entity.AdminSentenceEntity;
import hello.api.enumforentity.Grammar;
import hello.api.enumforentity.Situation;
import hello.api.exception.AdminSentenceException;
import hello.api.repository.AdminSentenceRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
//@Commit
class AdminSentenceServiceTest {

    @Autowired
    AdminSentenceService adminSentenceService;
    @Autowired
    AdminSentenceRepository adminSentenceRepository;

    @Test
    void saveSentence() {
        //given
        //when
        adminSentenceService.saveSentence("korean", "english", "HAVE PP", "NO");
        //then
        List<AdminSentenceEntity> all = adminSentenceRepository.findAll();
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
        AdminSentenceEntity save1 = adminSentenceRepository.save(
            new AdminSentenceEntity("korean1", "english1", Grammar.IF, Situation.NO));
        AdminSentenceEntity save2 = adminSentenceRepository.save(
            new AdminSentenceEntity("korean2", "english2", Grammar.NO, Situation.BUSINESS));
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
        AdminSentenceEntity save = adminSentenceRepository.save(
            new AdminSentenceEntity("korean1", "english1", Grammar.IF, Situation.NO));
        //when
        adminSentenceService.delete(save.getId());
        //then
        List<AdminSentenceDto> all = adminSentenceService.findAll();
        Assertions.assertThat(all).isEmpty();
    }
}