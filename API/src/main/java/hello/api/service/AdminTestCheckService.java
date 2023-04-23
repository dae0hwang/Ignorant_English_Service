package hello.api.service;

import hello.api.dto.AdminTestCheckRequest;
import hello.api.entity.AdminSentence;
import hello.api.entity.AdminTestCheck;
import hello.api.entity.Users;
import hello.api.enumforentity.Check;
import hello.api.repository.AdminSentenceRepository;
import hello.api.repository.AdminTestCheckRepository;
import hello.api.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminTestCheckService {

    private final AdminTestCheckRepository adminTestCheckRepository;
    private final AdminSentenceRepository adminSentenceRepository;
    private final UserRepository userRepository;
    @Transactional
    public void saveOrUpdateAdminTestCheck(AdminTestCheckRequest request) {
        Users findUser = userRepository.findById(request.getUserId()).orElseThrow();
        AdminSentence findAdminSentence = adminSentenceRepository.findById(request.getSentenceId())
            .orElseThrow();
        Optional<AdminTestCheck> findAdminTestCheck = adminTestCheckRepository.findByUsersAndAdminSentence(
            findUser, findAdminSentence);
        if (findAdminTestCheck.isPresent()) {
            //유저 id - 문장 id 일치하는 check 데이터가 있다면 데이터 수정
            AdminTestCheck adminTestCheck = findAdminTestCheck.get();
            adminTestCheck.ChangeTestCheck(Check.valueOf(request.getCheck()));

        } else {
            //없다면 새로운 데이터 삽입
            AdminTestCheck adminTestCheck = AdminTestCheck.builder().adminSentence(findAdminSentence)
                .users(findUser).testCheck(Check.valueOf(request.getCheck())).build();
            adminTestCheckRepository.save(adminTestCheck);
        }
    }
}
