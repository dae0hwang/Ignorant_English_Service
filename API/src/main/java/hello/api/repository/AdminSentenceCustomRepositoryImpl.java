package hello.api.repository;

import static hello.api.entity.QAdminSentence.adminSentence;
import static hello.api.entity.QAdminTestCheck.adminTestCheck;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.api.dto.AdminTestListConditionRequest;
import hello.api.enumforentity.Check;
import hello.api.enumforentity.Grammar;
import hello.api.enumforentity.Situation;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminSentenceCustomRepositoryImpl implements AdminSentenceCustomRepository{

    JPAQueryFactory queryFactory;

    public AdminSentenceCustomRepositoryImpl(EntityManager em) {
    this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Tuple> findTestListByCondition(AdminTestListConditionRequest condition) {
        Check check = Check.valueOf(condition.getCheck());
        List<Tuple> result = null;
        switch (check) {
            case CORRECT:
                result = queryFactory
                    .select(adminSentence.id, adminSentence.korean,
                        adminSentence.english, adminSentence.grammar, adminSentence.situation,
                        adminTestCheck.testCheck)
                    .from(adminSentence)
                    .where(grammarEq(condition.getGrammar()), situationEq(condition.getSituation()))
                    .innerJoin(adminSentence.adminTestChecks, adminTestCheck)
                    .on(adminTestCheck.testCheck.eq(check),
                        adminTestCheck.users.id.eq(condition.getUserId()))
                    .fetch();
                break;
            case NO:
                result = queryFactory
                    .select(adminSentence.id, adminSentence.korean,
                        adminSentence.english, adminSentence.grammar, adminSentence.situation,
                        adminTestCheck.testCheck)
                    .from(adminSentence)
                    .where(grammarEq(condition.getGrammar()), situationEq(condition.getSituation()))
                    .leftJoin(adminSentence.adminTestChecks, adminTestCheck)
                    .on(adminTestCheck.users.id.eq(condition.getUserId()))
                    .fetch();
                break;
            case WRONG:
                result = queryFactory
                    .select(adminSentence.id, adminSentence.korean,
                        adminSentence.english, adminSentence.grammar, adminSentence.situation,
                        adminTestCheck.testCheck)
                    .from(adminSentence)
                    .where(grammarEq(condition.getGrammar()), situationEq(condition.getSituation())
                            , adminTestCheck.isNull().or(adminTestCheck.testCheck.eq(Check.WRONG))
                    )
                    .leftJoin(adminSentence.adminTestChecks, adminTestCheck)
                    .on(adminTestCheck.users.id.eq(condition.getUserId()))
                    .fetch();
                break;
        }
        return result;
    }



    private BooleanExpression grammarEq(String stringGrammar) {
        //NO가 오면 where 절 적용 안함
        Grammar grammar = Grammar.valueOf(stringGrammar);
        return stringGrammar.equals("NO") ? null : adminSentence.grammar.eq(grammar);
    }

    private BooleanExpression situationEq(String stringSituation) {
        //NO가 오면 where 절 적용 안함
        Situation situation = Situation.valueOf(stringSituation);
        return stringSituation.equals("NO") ? null : adminSentence.situation.eq(situation);
    }




}
