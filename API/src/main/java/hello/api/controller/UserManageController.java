package hello.api.controller;

import hello.api.dto.UserInformationDto;
import hello.api.dto.UserSignupRequest;
import hello.api.service.EmailAuthService;
import hello.api.service.KafkaEmailService;
import hello.api.service.UserManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/user/manage")
public class UserManageController {

    private final UserManageService userManageService;
    private final KafkaEmailService kafkaEmailService;
    private final EmailAuthService emailAuthService;

    @PostMapping("/signup")
    @Operation(responses = {
        @ApiResponse(responseCode = "409", description = "회원 가입 email id가 중복"),
        @ApiResponse(responseCode = "200", description = "회원 가입 요청 성공")
    })
    public ResponseEntity<Void> signup(
        @Valid @RequestBody UserSignupRequest userSignupRequest) {
        userManageService.signup(userSignupRequest);
        emailAuthService.save(userSignupRequest.getUsername());
        kafkaEmailService.sendMessage(userSignupRequest.getUsername());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/check")
    public ResponseEntity<Void> check() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/information")
    public ResponseEntity<UserInformationDto> getInformation(Authentication authentication) {
        UserInformationDto userInformation = userManageService.getUserInformation(authentication);
        return new ResponseEntity<>(userInformation, HttpStatus.OK);
    }
}
