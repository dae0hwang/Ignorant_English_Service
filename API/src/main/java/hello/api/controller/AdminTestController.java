package hello.api.controller;

import hello.api.dto.AdminTestCheckRequest;
import hello.api.dto.AdminTestListConditionRequest;
import hello.api.dto.AdminTestSentenceDto;
import hello.api.service.AdminSentenceService;
import hello.api.service.AdminTestCheckService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/test")
public class AdminTestController {

    private final AdminSentenceService adminSentenceService;
    private final AdminTestCheckService adminTestCheckService;

    @PutMapping("/list")
    public ResponseEntity<List<AdminTestSentenceDto>> listByCondition(
        @RequestBody AdminTestListConditionRequest request) {
        List<AdminTestSentenceDto> adminTestListByCondition =
            adminSentenceService.findAdminTestListByCondition(request);
        return new ResponseEntity<>(adminTestListByCondition, HttpStatus.OK);
    }

    @PostMapping("/check")
    public ResponseEntity<Void> TestCorrect(@RequestBody AdminTestCheckRequest request) {
        adminTestCheckService.saveOrUpdateAdminTestCheck(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
