package hello.api.repository;

import com.querydsl.core.Tuple;
import hello.api.dto.AdminTestListConditionRequest;
import java.util.List;

public interface AdminSentenceCustomRepository {


    List<Tuple> findTestListByCondition(AdminTestListConditionRequest condition);
}
