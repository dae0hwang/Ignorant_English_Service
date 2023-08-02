package hello.api.enumforexception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Slf4j
public enum UserManageExceptionEnum {
    DUPLICATED_SIGNUP_EMAIL(Constants.duplicatedSignupEmail,
        "userManageDuplicatedEmailException", "/errors/user/manage/signup/duplicated-email",
        HttpStatus.CONFLICT),
    SIGNUP_REQUEST_PASSWORD(Constants.signupRequestPassword, "signupPasswordValidationException",
        "/errors/user/manage/signup/password-pattern" , HttpStatus.BAD_REQUEST),
    SIGNUP_REQUEST_EMAIL(Constants.signupRequestEmail, "signupEmailValidationException",
        "/errors/user/manage/signup/email-pattern" , HttpStatus.BAD_REQUEST),
    SIGNUP_REQUEST_NAME(Constants.signupRequestName, "signupNameValidationException",
        "/errors/user/manage/signup/name-pattern" , HttpStatus.BAD_REQUEST),
    DUPLICATED_SUBSCRIBE(Constants.duplicatedSubscribe, "duplicatedSubscribeException",
        "/errors/user/subscribe/duplicate", HttpStatus.BAD_REQUEST)

    ;

    private final String errormessage;
    private final String errorTitle;
    private final String errorType;
    private final HttpStatus httpStatus;

    public static UserManageExceptionEnum findByErrorMessage(String errormessage) {
        for (UserManageExceptionEnum exceptionEnum : UserManageExceptionEnum.values()) {
            if (exceptionEnum.getErrormessage().equals(errormessage)) {
                return exceptionEnum;
            }
        }
        throw new RuntimeException();
    }

    public static class Constants {
        public static final String duplicatedSignupEmail =
            "signup email can't be duplicated";
        public static final String signupRequestPassword =
            "signup Request password is At least 8 characters, at least 1 alphabet, number,"
                + " and special character each";
        public static final String signupRequestEmail =
            "signup Request email must be email pattern";
        public static final String signupRequestName =
            "signup Request email must not be blank";
        public static final String duplicatedSubscribe =
            "duplicated subscribed sentence";
    }
}
