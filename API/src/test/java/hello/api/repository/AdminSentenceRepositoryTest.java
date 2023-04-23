package hello.api.repository;

import static hello.api.entity.QAdminSentence.adminSentence;
import static hello.api.entity.QAdminTestCheck.adminTestCheck;

import com.querydsl.core.Tuple;
import hello.api.dto.AdminTestListConditionRequest;
import hello.api.enumforentity.Check;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
class AdminSentenceRepositoryTest {

    @Autowired
    AdminSentenceRepository adminSentenceRepository;
    @Test
    void grammarSituationTest() {
//        //given
//        //이러면 전부 나와야 한다.
//        AdminTestListConditionRequest request = new AdminTestListConditionRequest(null, "NO", "NO",
//            null);
//        List<AdminSentence> result = adminSentenceRepository.findTestListByCondition(
//            request);
//        System.out.println("size = "+ result.size());
//        for (AdminSentence adminSentence : result) {
//            System.out.println(adminSentence.getKorean());
//        }


        //given
        //이러면 전부 나와야 한다.
//        AdminTestListConditionRequest request = new AdminTestListConditionRequest(null, "NO", "BUSINESS",
//            null);
//        List<AdminSentence> result = adminSentenceRepository.findTestListByCondition(
//            request);
//        System.out.println("size = "+ result.size());
//        for (AdminSentence adminSentence : result) {
//            System.out.println(adminSentence.getKorean());
//        }
        //ㅇㅇ 전부 맞음

    }

    @Test
    void 최종테스트2() {
        //given
        //전부 출력..
        AdminTestListConditionRequest request1 = new AdminTestListConditionRequest(6L, "NO", "NO",
            "NO");

        AdminTestListConditionRequest request2 = new AdminTestListConditionRequest(4L, "NO", "NO",
            "CORRECT");

        AdminTestListConditionRequest request3 = new AdminTestListConditionRequest(4L, "NO", "NO",
            "WRONG");
        List<Tuple> result = adminSentenceRepository.findTestListByCondition(request3);
        System.out.println("size= "+ result.size());

        for (Tuple tuple : result) {
            Long id = tuple.get(adminSentence.id);
            String korean = tuple.get(adminSentence.korean);
//            List<AdminTestCheck> adminTestChecks = tuple.get(adminSentence.adminTestChecks);
            System.out.print(id);
            System.out.print(korean);
//            if (!adminTestChecks.isEmpty()) {
//                System.out.print(adminTestChecks.get(0).getUsers().getId());
//            }
            Long userId = tuple.get(adminTestCheck.users.id);
            System.out.print(userId!=null ? userId:"nullID ");
            Check check = tuple.get(adminTestCheck.testCheck);
            System.out.print(check != null ? check.getStringCheck() : null);

            System.out.println();
        }
    }
}