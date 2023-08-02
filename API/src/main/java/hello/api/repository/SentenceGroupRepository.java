package hello.api.repository;

import hello.api.entity.SentenceGroup;
import hello.api.entity.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceGroupRepository extends JpaRepository<SentenceGroup, Long> {

    List<SentenceGroup> findListByUser(Users users);
    List<SentenceGroup> findListByUserNot(Users users);
}
