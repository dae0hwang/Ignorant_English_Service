package hello.api.service;

import hello.api.dto.AdminSentenceDto;
import hello.api.entity.AdminSentenceEntity;
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
        AdminSentenceEntity adminSentenceEntity = new AdminSentenceEntity(korean, english,
            grammar, situation);
        adminSentenceRepository.save(adminSentenceEntity);
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
        List<AdminSentenceEntity> entityList = adminSentenceRepository.findAll();
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
        AdminSentenceEntity entity = adminSentenceRepository.findById(id)
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
        AdminSentenceEntity adminSentenceEntity = adminSentenceRepository.findById(id)
            .orElseThrow();
        adminSentenceEntity.setKorean(korean);
        adminSentenceEntity.setEnglish(english);
        adminSentenceEntity.setGrammar(grammar);
        adminSentenceEntity.setSituation(situation);
    }
}
