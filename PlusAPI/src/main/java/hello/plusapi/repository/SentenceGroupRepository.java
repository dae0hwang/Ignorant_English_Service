package hello.plusapi.repository;

import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceGroupRepository extends JpaRepository<SentenceGroup, Long> {

    List<SentenceGroup> findListByUser(Users users);

    List<SentenceGroup> findListByUserNot(Users users);
}
