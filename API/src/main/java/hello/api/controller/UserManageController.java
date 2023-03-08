package hello.api.controller;

import hello.api.dto.UserSignupRequest;
import hello.api.service.UserManageService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/manage")
public class UserManageController {

    private final UserManageService userManageService;

    @PostMapping("/signup")
    public ResponseEntity signup(
        @Valid @RequestBody UserSignupRequest userSignupRequest) {
        userManageService.signup(userSignupRequest);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
