package hello.api.enumfortest;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TestResultEnum {
    HINT("HINT"),
    CORRECT("CORRECT"),
    WRONG("WRONG"),
    TEST_TIME("TEST_TIME");
    private final String stringTestResult;
}
