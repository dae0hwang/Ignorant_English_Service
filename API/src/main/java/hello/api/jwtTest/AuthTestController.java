package hello.api.jwtTest;

import hello.api.entity.Users;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AuthTestController {

    private final UserTestService userTestService;

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Users> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(userTestService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Users> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userTestService.getUserWithAuthorities(username));
    }

}
