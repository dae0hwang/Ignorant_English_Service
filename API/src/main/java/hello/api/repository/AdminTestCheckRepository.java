package hello.api.repository;

import hello.api.entity.AdminSentence;
import hello.api.entity.AdminTestCheck;
import hello.api.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminTestCheckRepository extends JpaRepository<AdminTestCheck, Long> {

    Optional<AdminTestCheck> findByUsersAndAdminSentence(Users users, AdminSentence adminSentence);


}
