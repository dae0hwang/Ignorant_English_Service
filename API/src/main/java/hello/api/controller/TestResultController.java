package hello.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.api.dto.TestResultRequest;
import hello.api.service.TestResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/result")
@RequiredArgsConstructor
@Slf4j
public class TestResultController {

    private final TestResultService testResultService;

    @PostMapping("/correct")
    public ResponseEntity<Void> correctResult(@RequestBody TestResultRequest request)
        throws JsonProcessingException {
        testResultService.correctMessage(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/hint")
    public ResponseEntity<Void> hintResult(@RequestBody TestResultRequest request)
        throws JsonProcessingException {
        testResultService.hintMessage(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/wrong")
    public ResponseEntity<Void> wrongResult(@RequestBody TestResultRequest request)
        throws JsonProcessingException {
        testResultService.wrongMessage(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/time/start")
    public ResponseEntity<Void> startResult(@RequestBody TestResultRequest request) {
        testResultService.sendStartTimeToRedis(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/time/end")
    public ResponseEntity<Void> endResult(@RequestBody TestResultRequest request)
        throws JsonProcessingException {
        testResultService.TestTimeMessage(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
