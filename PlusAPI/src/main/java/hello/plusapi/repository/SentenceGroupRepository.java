package hello.plusapi.repository;

import hello.plusapi.entity.SentenceGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceGroupRepository extends JpaRepository<SentenceGroup, Long> {

}
