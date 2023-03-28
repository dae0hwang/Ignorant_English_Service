package hello.plusapi.exceptionadvice;

import static hello.plusapi.enumforexception.EmailAuthExceptionEnum.EXPIRED_EMAILAUTHTOKEN;
import static hello.plusapi.enumforexception.EmailAuthExceptionEnum.findByErrorMessage;

import hello.plusapi.controller.EmailAuthController;
import hello.plusapi.exception.EmailAuthException;
import hello.plusapi.exception.ErrorInformationTlsException;
import hello.plusapi.threadlocalstorage.ErrorInformation;
import hello.plusapi.threadlocalstorage.ErrorInformationTlsContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = {EmailAuthController.class})
public class EmailAuthExceptionAdvice {

    private final ErrorInformationTlsContainer errorInformationTlsContainer;


    @ExceptionHandler(EmailAuthException.class)
    public ResponseEntity companyFoodExceptionAdvice(EmailAuthException e) {
        log.warn("[exceptionAdvice] ex", e);
        return makeEmailAuthExceptionResponseAndSetTls(e);
    }

    private ResponseEntity makeEmailAuthExceptionResponseAndSetTls(
        EmailAuthException e) {
        ThreadLocal<ErrorInformation> threadLocal = errorInformationTlsContainer.getThreadLocal();
        if (!threadLocal.get().getErrorTitle().equals("none")) {
            throw new ErrorInformationTlsException("errorInformationTls is not Empty");
        }

        String errorMessage = e.getMessage();
        ErrorInformation errorInformation = new ErrorInformation();
        switch (findByErrorMessage(errorMessage)) {
            case EXPIRED_EMAILAUTHTOKEN:
                errorInformation.setErrorType(EXPIRED_EMAILAUTHTOKEN.getErrorType());
                errorInformation.setErrorTitle(EXPIRED_EMAILAUTHTOKEN.getErrorTitle());
                errorInformation.setErrorDetail(EXPIRED_EMAILAUTHTOKEN.getErrormessage());
                threadLocal.set(errorInformation);
                return new ResponseEntity(EXPIRED_EMAILAUTHTOKEN.getHttpStatus());
        }
        throw new RuntimeException();
    }
}
