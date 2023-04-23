package hello.api.service;

import static hello.api.entity.QAdminSentence.adminSentence;
import static hello.api.entity.QAdminTestCheck.adminTestCheck;

import com.querydsl.core.Tuple;
import hello.api.dto.AdminSentenceDto;
import hello.api.dto.AdminTestListConditionRequest;
import hello.api.dto.AdminTestSentenceDto;
import hello.api.entity.AdminSentence;
import hello.api.enumforentity.Grammar;
import hello.api.enumforentity.Situation;
import hello.api.enumforexception.AdminSentenceExceptionEnum.Constants;
import hello.api.exception.AdminSentenceException;
import hello.api.repository.AdminSentenceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminSentenceService {

    private final AdminSentenceRepository adminSentenceRepository;

    @Transactional
    public void saveSentence(String korean, String english, String stringGrammar,
        String stringSituation) {
        Grammar grammar = findGrammar(stringGrammar);
        Situation situation = findSituation(stringSituation);
        AdminSentence adminSentence = new AdminSentence(korean, english,
            grammar, situation);
        adminSentenceRepository.save(adminSentence);
    }

    @Transactional(readOnly = true)
    public List<String> findGrammarValues() {
        List<String> list = new ArrayList<>();
        for (Grammar grammar : Grammar.values()) {
            list.add(grammar.getStringGrammar());
        }
        return list;
    }

    @Transactional(readOnly = true)
    public List<String> findSituationValues() {
        List<String> list = new ArrayList<>();
        for (Situation situation : Situation.values()) {
            list.add(situation.getStringSituation());
        }
        return list;
    }

    private Grammar findGrammar(String stringGrammar) {
        Grammar findGrammar = null;
        for (Grammar searchGrammar : Grammar.values()) {
            if (searchGrammar.getStringGrammar().equals(stringGrammar)) {
                findGrammar = searchGrammar;
            }
        }
        if (findGrammar == null) {
            throw new AdminSentenceException(Constants.noMatchGrammarEnum);
        } else {
            return findGrammar;
        }
    }

    private Situation findSituation(String stringSituation) {
        Situation findSituation = null;
        for (Situation searchSituation : Situation.values()) {
            if (searchSituation.getStringSituation().equals(stringSituation)) {
                findSituation = searchSituation;
            }
        }
        if (findSituation == null) {
            throw new AdminSentenceException(Constants.noMatchSituationEnum);
        } else {
            return findSituation;
        }
    }

    @Transactional(readOnly = true)
    public List<AdminSentenceDto> findAll() {
        List<AdminSentence> entityList = adminSentenceRepository.findAll();
        return entityList.stream()
            .map(e -> new AdminSentenceDto(e.getId(), e.getKorean(), e.getEnglish(),
                e.getGrammar().getStringGrammar(), e.getSituation().getStringSituation())).collect(
                Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        adminSentenceRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AdminSentenceDto findById(Long id) {
        AdminSentence entity = adminSentenceRepository.findById(id)
            .orElseThrow();
        return new AdminSentenceDto(entity.getId(), entity.getKorean(),
            entity.getEnglish(), entity.getGrammar().getStringGrammar(),
            entity.getSituation().getStringSituation());
    }

    @Transactional
    public void update(Long id, String korean, String english, String stringGrammar,
        String stringSituation) {
        Grammar grammar = findGrammar(stringGrammar);
        Situation situation = findSituation(stringSituation);
        updateEntity(id, korean, english, grammar, situation);
    }

    private void updateEntity(Long id, String korean, String english, Grammar grammar,
        Situation situation) {
        AdminSentence adminSentence = adminSentenceRepository.findById(id)
            .orElseThrow();
        adminSentence.setKorean(korean);
        adminSentence.setEnglish(english);
        adminSentence.setGrammar(grammar);
        adminSentence.setSituation(situation);
    }

    @Transactional(readOnly = true)
    public List<AdminTestSentenceDto> findAdminTestListByCondition(
        AdminTestListConditionRequest request) {
        List<Tuple> testListByCondition = adminSentenceRepository.findTestListByCondition(request);
        List<AdminTestSentenceDto> adminTestSentenceDtoList = makeAdminTestSentenceDto(
            testListByCondition);
        return adminTestSentenceDtoList;
    }
    private List<AdminTestSentenceDto> makeAdminTestSentenceDto(List<Tuple> queryResultList) {
        List<AdminTestSentenceDto> dtoList = new ArrayList<>();
        for (Tuple tuple : queryResultList) {
            Long adminSentenceId = tuple.get(adminSentence.id);
            String korean = tuple.get(adminSentence.korean);
            String english = tuple.get(adminSentence.english);
            String hint = makeEnglishHint(english);
            String stringGrammar = tuple.get(adminSentence.grammar).getStringGrammar();
            String stringSituation = tuple.get(adminSentence.situation).getStringSituation();
            String stringTestCheck =
                tuple.get(adminTestCheck.testCheck) != null ? tuple.get(adminTestCheck.testCheck)
                    .getStringCheck() : "NO";
            AdminTestSentenceDto dto = new AdminTestSentenceDto().builder()
                .sentenceId(adminSentenceId).korean(korean)
                .english(english).hint(hint).grammar(stringGrammar).situation(stringSituation)
                .check(stringTestCheck).build();
            dtoList.add(dto);
        }
        return dtoList;
    }

    private String makeEnglishHint(String english) {
        String[] split = english.split(" ");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i % 2 == 0) {
                //0이나 짝수 인덱스 이면 "_"으로 제공
                result.append("_".repeat(split[i].length())).append(" ");
            } else {
                //홀수면 문자 그대로 제공
                result.append(split[i]).append(" ");
            }
        }
        return result.toString();
    }
}
