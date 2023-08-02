package hello.api.exceptionadvice;

import static hello.api.enumforexception.UserManageExceptionEnum.DUPLICATED_SIGNUP_EMAIL;
import static hello.api.enumforexception.UserManageExceptionEnum.DUPLICATED_SUBSCRIBE;

import hello.api.controller.UserSentenceController;
import hello.api.exception.DuplicatedSubscribeException;
import hello.api.exception.ErrorInformationTlsException;
import hello.api.threadlocalstorage.ErrorInformation;
import hello.api.threadlocalstorage.ErrorInformationTlsContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = {UserSentenceController.class})
public class UserSentenceExceptionAdvice {

    private final ErrorInformationTlsContainer errorInformationTlsContainer;
    @ExceptionHandler(DuplicatedSubscribeException.class)
    public ResponseEntity<Void> duplicatedSubscribeExceptionAdvice(DuplicatedSubscribeException e) {
        log.info("들어오니??");
        log.warn("[exceptionAdvice] ex", e);
        ThreadLocal<ErrorInformation> threadLocal = errorInformationTlsContainer.getThreadLocal();
        if (!threadLocal.get().getErrorTitle().equals("none")) {
            throw new ErrorInformationTlsException("errorInformationTls is not Empty");
        }
        ErrorInformation errorInformation = new ErrorInformation();
        errorInformation.setErrorType(DUPLICATED_SUBSCRIBE.getErrorType());
        errorInformation.setErrorTitle(DUPLICATED_SUBSCRIBE.getErrorTitle());
        errorInformation.setErrorDetail(DUPLICATED_SUBSCRIBE.getErrormessage());
        threadLocal.set(errorInformation);
        return new ResponseEntity<>(DUPLICATED_SIGNUP_EMAIL.getHttpStatus());
    }
}
