package hello.api.repository;


import hello.api.entity.AdminSentenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSentenceRepository extends JpaRepository<AdminSentenceEntity, Long> {

}
