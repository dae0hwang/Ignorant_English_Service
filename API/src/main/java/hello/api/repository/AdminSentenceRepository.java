package hello.api.repository;


import hello.api.entity.AdminSentence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminSentenceRepository extends JpaRepository<AdminSentence, Long>,
    AdminSentenceCustomRepository {

}
