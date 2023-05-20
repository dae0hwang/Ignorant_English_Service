package hello.plusapi.controller;

import hello.plusapi.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/email")
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @GetMapping("/auth")
    public ResponseEntity<String> authEmail(@RequestParam("authToken") String authToken) {
        emailAuthService.confirmEmail(authToken);
        return new ResponseEntity("success", HttpStatus.OK);
    }
}