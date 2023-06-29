package hello.api.controller;

import hello.api.dto.UserInformationDto;
import hello.api.dto.UserSignupRequest;
import hello.api.oauth.AccessToken;
import hello.api.oauth.Profile;
import hello.api.service.EmailAuthService;
import hello.api.service.OAuthService;
import hello.api.service.SqsEmailService;
import hello.api.service.UserManageService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/user/manage")
public class UserManageController {

    private final UserManageService userManageService;
    private final SqsEmailService sqsEmailService;
    private final EmailAuthService emailAuthService;
    private final OAuthService oAuthService;
    @Value("${redirect.ui.url}")
    private String redirectUiUrl;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
        @Valid @RequestBody UserSignupRequest userSignupRequest) {
        userManageService.signup(userSignupRequest);
        emailAuthService.save(userSignupRequest.getUsername());
        sqsEmailService.sendMessage(userSignupRequest.getUsername());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //토큰이 있어야 들어올 수 있는 곳이다.
    @GetMapping("/check")
    public ResponseEntity<Void> check() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/information")
    public ResponseEntity<UserInformationDto> getInformation(Authentication authentication) {
        UserInformationDto userInformation = userManageService.getUserInformation(authentication);
        return new ResponseEntity<>(userInformation, HttpStatus.OK);
    }

    //google 인증 폼 주소를 제공하는 컨트롤러
    //인증을 하게 되면 해당 백엔드 서버 /social/login/redirect URL redirect 되어 auth 토큰을 반환한다.
    @GetMapping("/social/login")
    public ResponseEntity<Void> socialLogin() {
        String googleLoginUrl = oAuthService.getGoogleLoginUrl();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Login-Url", googleLoginUrl);
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);
    }

    //해당 auth 토큰으로 -> access token -> profile 정보 받기
    // -> DB 등록 -> JWT 토큰 발행
    // -> 이미 DB에 등록되어 있다면 -> JWT 토큰만 발행
    @GetMapping("/social/login/redirect")
    public void socialLoginRedirect(@RequestParam String code, HttpServletResponse response)
        throws IOException {
        AccessToken accessToken = oAuthService.getAccessToken(code);
        Profile profile = oAuthService.getProfile(accessToken.getAccess_token());
        String jwtToken = oAuthService.checkProfileAndCreateToken(profile);
        response.sendRedirect(redirectUiUrl+jwtToken);
    }
}

