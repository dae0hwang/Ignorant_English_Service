package hello.api.service;

import hello.api.entity.AdminSentenceEntity;
import hello.api.repository.AdminSentenceRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
//@Commit
class AdminSentenceServiceTest {

    @Autowired
    AdminSentenceService adminSentenceService;
    @Autowired
    AdminSentenceRepository adminSentenceRepository;

    @Test
    void saveSentence() {
        //given
        //when
        adminSentenceService.saveSentence("korean", "english", "HAVE PP", "NO");
        //then
        List<AdminSentenceEntity> all = adminSentenceRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(1);
    }

}