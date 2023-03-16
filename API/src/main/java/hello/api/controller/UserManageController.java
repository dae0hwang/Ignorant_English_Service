package hello.api.controller;

import hello.api.dto.UserInformationDto;
import hello.api.dto.UserSignupRequest;
import hello.api.service.KafkaService;
import hello.api.service.UserManageService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/user/manage")
public class UserManageController {

    private final UserManageService userManageService;
    private final KafkaService kafkaService;

    @PostMapping("/signup")
    public ResponseEntity signup(
        @Valid @RequestBody UserSignupRequest userSignupRequest) {
        kafkaService.sendEmailMessage(userSignupRequest.getUsername());
        userManageService.signup(userSignupRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/check")
    public ResponseEntity check() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/information")
    public ResponseEntity<UserInformationDto> getInformation(Authentication authentication) {
        UserInformationDto userInformation = userManageService.getUserInformation(authentication);
        return new ResponseEntity<>(userInformation, HttpStatus.OK);
    }
}
