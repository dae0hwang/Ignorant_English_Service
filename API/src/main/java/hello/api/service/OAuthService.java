package hello.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import hello.api.entity.Users;
import hello.api.exception.CommunicationException;
import hello.api.jwt.JwtProperties;
import hello.api.oauth.AccessToken;
import hello.api.oauth.OAuthRequest;
import hello.api.oauth.Profile;
import hello.api.repository.UserRepository;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final Gson gson;
    @Value("${spring.social.google.url.login}")
    private String googleLoginUrl;
    @Value("${spring.social.google.client_id}")
    String googleClientId;
    @Value("${spring.social.google.redirect}")
    String googleRedirect;
    @Value("${spring.social.google.client_secret}")
    String googleClientSecret;
    @Value("${spring.social.google.url.token}")
    private String googleTokenUrl;
    @Value("${spring.social.google.url.profile}")
    private String googleProfileUrl;

    public String getGoogleLoginUrl() {
        StringBuilder loginUrl = new StringBuilder()
            .append(googleLoginUrl)
            .append("?client_id=").append(googleClientId)
            .append("&response_type=code")
            .append("&scope=email%20profile")
            .append("&redirect_uri=").append(googleRedirect);
        return loginUrl.toString();
    }

    public AccessToken getAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        OAuthRequest oAuthRequest = getRequest(code);
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(
            oAuthRequest.getMap(), httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(googleTokenUrl,
            request, String.class);
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return gson.fromJson(response.getBody(), AccessToken.class);
            }
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }

    private OAuthRequest getRequest(String code) {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", googleClientId);
        map.add("client_secret", googleClientSecret);
        map.add("redirect_uri", googleRedirect);
        map.add("code", code);
        return new OAuthRequest(googleTokenUrl, map);
    }

    public Profile getProfile(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(googleProfileUrl, request,
            String.class);
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                return gson.fromJson(response.getBody(), Profile.class);
            }
        } catch (Exception e) {
            throw new CommunicationException();
        }
        throw new CommunicationException();
    }

    @Transactional
    public String checkProfileAndCreateToken(Profile profile) {
        Optional<Users> byUsername = userRepository.findByUsername(profile.getEmail());
        //이미 DB에 유저 정보가 저장되어 있다면, JWT 토큰만 발급
        if (byUsername.isPresent()) {
            Users users = byUsername.orElseThrow();
            return createJwtToken(users.getId(), users.getUsername());
        } else {
            //DB에 존재하지 않는다면 DB 저장 후 JWT 토큰 발급
            Users save = userRepository.save(
                Users.builder().username(profile.getEmail()).name(profile.getName())
                    .provider("google").roles("ROLE_USER").emailAuth(false).password("NO").build());
            return createJwtToken(save.getId(), save.getUsername());
        }
    }

    private String createJwtToken(Long id, String username) {
        String jwtToken = JWT.create()
            .withSubject(username)
            .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
            .withClaim("id", id)
            .withClaim("username", username)
            .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        return "Bearer "+jwtToken;
    }
}
