package hello.api.service;

import hello.api.entity.AdminSentenceEntity;
import hello.api.enumforentity.Grammar;
import hello.api.enumforentity.Situation;
import hello.api.repository.AdminSentenceRepository;
import java.util.ArrayList;
import java.util.List;
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

    private static Grammar findGrammar(String stringGrammar) {
        Grammar findGrammar = Grammar.NO;
        for (Grammar searchGrammar : Grammar.values()) {
            if (searchGrammar.getStringGrammar().equals(stringGrammar)) {
                findGrammar = searchGrammar;
            }
        }
        return findGrammar;
    }

    private static Situation findSituation(String stringSituation) {
        Situation findSituation = Situation.NO;
        for (Situation searchSituation : Situation.values()) {
            if (searchSituation.getStringSituation().equals(stringSituation)) {
                findSituation = searchSituation;
            }
        }
        return findSituation;
    }
}
