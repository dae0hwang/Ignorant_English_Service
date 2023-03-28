package hello.plusapi.enumforexception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Slf4j
public enum EmailAuthExceptionEnum {

    EXPIRED_EMAILAUTHTOKEN(EmailAuthExceptionEnum.Constants.expiredEmailAuthToken,
        "expiredEmailAuthTokenException", "/errors/email/auth/expired",
        HttpStatus.BAD_REQUEST),
    NOEXIST_EMAILAUTHTOKEN(Constants.noExistEmailAuthToken, "noExistEmailAuthToken",
        "/errors/email/auth/no_exist", HttpStatus.BAD_REQUEST)
    ;

    private final String errormessage;
    private final String errorTitle;
    private final String errorType;
    private final HttpStatus httpStatus;

    public static EmailAuthExceptionEnum findByErrorMessage(String errormessage) {
        for (EmailAuthExceptionEnum exceptionEnum : EmailAuthExceptionEnum.values()) {
            if (exceptionEnum.getErrormessage().equals(errormessage)) {
                return exceptionEnum;
            }
        }
        throw new RuntimeException();
    }

    public static class Constants {
        public static final String expiredEmailAuthToken =
            "email authToken is expired";
        public static final String noExistEmailAuthToken =
            "this email authToken is not exist";
    }
}
