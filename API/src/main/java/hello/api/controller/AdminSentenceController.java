package hello.api.controller;

import hello.api.dto.AdminSentenceRequest;
import hello.api.dto.AdminSentenceSuccess;
import hello.api.service.AdminSentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/sentence")
public class AdminSentenceController {

    private final AdminSentenceService adminSentenceService;

    @PostMapping("/add")
    public ResponseEntity<AdminSentenceSuccess> addSentence(@RequestBody AdminSentenceRequest request) {
        adminSentenceService.saveSentence(request.getKorean(), request.getEnglish(),
            request.getGrammar(), request.getSituation());
        AdminSentenceSuccess success = new AdminSentenceSuccess(201, null, null);
        return new ResponseEntity<>(success, HttpStatus.CREATED);
    }

}
