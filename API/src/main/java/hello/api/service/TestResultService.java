package hello.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.api.dto.TestResultDto;
import hello.api.dto.TestResultRequest;
import hello.api.enumfortest.TestResultEnum;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestResultService {

    private final RedisTemplate<String,String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void correctMessage(TestResultRequest request) throws JsonProcessingException {
        TestResultDto testResult = new TestResultDto(request.getUserId(), request.getSentenceId(),
            TestResultEnum.CORRECT.getStringTestResult(), null);
        String serializedResult = objectMapper.writeValueAsString(testResult);
        log.info(serializedResult);
    }

    public void hintMessage(TestResultRequest request) throws JsonProcessingException {
        TestResultDto testResult = new TestResultDto(request.getUserId(), request.getSentenceId(),
            TestResultEnum.HINT.getStringTestResult(), null);
        String serializedResult = objectMapper.writeValueAsString(testResult);
        log.info(serializedResult);
    }

    public void wrongMessage(TestResultRequest request) throws JsonProcessingException {
        TestResultDto testResult = new TestResultDto(request.getUserId(), request.getSentenceId(),
            TestResultEnum.WRONG.getStringTestResult(), null);
        String serializedResult = objectMapper.writeValueAsString(testResult);
        log.info(serializedResult);
    }

    public void sendStartTimeToRedis(TestResultRequest request) {
        String redisKey = "U" + request.getUserId() + "S"
            + request.getSentenceId();
        String nowDateTime = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        redisTemplate.opsForValue().set(redisKey, nowDateTime);
        redisTemplate.expire(redisKey, 300, TimeUnit.SECONDS);
        //이거 한번 테스트해보기.a
    }

    public void TestTimeMessage(TestResultRequest request) throws JsonProcessingException {
        LocalTime testTime = getTestTime(request);
        TestResultDto testResult = new TestResultDto(request.getUserId(), request.getSentenceId(),
            TestResultEnum.TEST_TIME.getStringTestResult(), testTime);
        String serializedResult = objectMapper.writeValueAsString(testResult);
        log.info(serializedResult);
    }

    private LocalTime getTestTime(TestResultRequest request) {
        String redisKey = "U" + request.getUserId() + "S"
            + request.getSentenceId();
        String getDateTime = redisTemplate.opsForValue().get(redisKey);
        assert getDateTime != null;
        LocalDateTime parseLocalDateTime = LocalDateTime.parse(getDateTime,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Duration diff = Duration.between(parseLocalDateTime, LocalDateTime.now());

        LocalTime testTime = LocalTime.of(diff.toHoursPart(), diff.toMinutesPart(),
            diff.toSecondsPart(), diff.getNano());
        return testTime;
    }
}
