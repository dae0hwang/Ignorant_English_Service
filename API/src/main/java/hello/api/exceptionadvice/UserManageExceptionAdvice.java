package hello.api.exceptionadvice;

import static hello.api.enumforexception.UserManageExceptionEnum.DUPLICATED_SIGNUP_EMAIL;
import static hello.api.enumforexception.UserManageExceptionEnum.SIGNUP_REQUEST_EMAIL;
import static hello.api.enumforexception.UserManageExceptionEnum.SIGNUP_REQUEST_NAME;
import static hello.api.enumforexception.UserManageExceptionEnum.SIGNUP_REQUEST_PASSWORD;

import hello.api.controller.UserManageController;
import hello.api.enumforexception.UserManageExceptionEnum;
import hello.api.exception.ErrorInformationTlsException;
import hello.api.exception.UserManageException;
import hello.api.threadlocalstorage.ErrorInformation;
import hello.api.threadlocalstorage.ErrorInformationTlsContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = {UserManageController.class})
public class UserManageExceptionAdvice {

    private final ErrorInformationTlsContainer errorInformationTlsContainer;

    @ExceptionHandler(UserManageException.class)
    public ResponseEntity<Void> userManageExceptionAdvice(UserManageException e) {
        log.warn("[exceptionAdvice] ex", e);
        return userManageExceptionResponseAndSetTls(e);
    }

    private ResponseEntity<Void> userManageExceptionResponseAndSetTls(UserManageException e) {
        ThreadLocal<ErrorInformation> threadLocal = errorInformationTlsContainer.getThreadLocal();
        if (!threadLocal.get().getErrorTitle().equals("none")) {
            throw new ErrorInformationTlsException("errorInformationTls is not Empty");
        }

        String errorMessage = e.getMessage();
        ErrorInformation errorInformation = new ErrorInformation();
        switch (UserManageExceptionEnum.findByErrorMessage(errorMessage)) {
            case DUPLICATED_SIGNUP_EMAIL:
                errorInformation.setErrorType(DUPLICATED_SIGNUP_EMAIL.getErrorType());
                errorInformation.setErrorTitle(DUPLICATED_SIGNUP_EMAIL.getErrorTitle());
                errorInformation.setErrorDetail(DUPLICATED_SIGNUP_EMAIL.getErrormessage());
                threadLocal.set(errorInformation);
                return new ResponseEntity<>(DUPLICATED_SIGNUP_EMAIL.getHttpStatus());
        }
        throw new RuntimeException();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> methodArgumentNotValidExceptionAdvice(MethodArgumentNotValidException e) {
        log.warn("[exceptionAdvice] ex", e);
        return methodArgumentNotValidExceptionResponseAndSetTls(e);
    }

    private ResponseEntity<Void> methodArgumentNotValidExceptionResponseAndSetTls(
        MethodArgumentNotValidException e) {
        ThreadLocal<ErrorInformation> threadLocal = errorInformationTlsContainer.getThreadLocal();
        if (!threadLocal.get().getErrorTitle().equals("none")) {
            throw new ErrorInformationTlsException("errorInformationTls is not Empty");
        }

        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorInformation errorInformation = new ErrorInformation();
        switch (UserManageExceptionEnum.findByErrorMessage(errorMessage)) {
            case SIGNUP_REQUEST_EMAIL:
                errorInformation.setErrorType(SIGNUP_REQUEST_EMAIL.getErrorType());
                errorInformation.setErrorTitle(SIGNUP_REQUEST_EMAIL.getErrorTitle());
                errorInformation.setErrorDetail(SIGNUP_REQUEST_EMAIL.getErrormessage());
                threadLocal.set(errorInformation);
                return new ResponseEntity<>(SIGNUP_REQUEST_EMAIL.getHttpStatus());
            case SIGNUP_REQUEST_NAME:
                errorInformation.setErrorType(SIGNUP_REQUEST_NAME.getErrorType());
                errorInformation.setErrorTitle(SIGNUP_REQUEST_NAME.getErrorTitle());
                errorInformation.setErrorDetail(SIGNUP_REQUEST_NAME.getErrormessage());
                threadLocal.set(errorInformation);
                return new ResponseEntity<>(SIGNUP_REQUEST_NAME.getHttpStatus());
            case SIGNUP_REQUEST_PASSWORD:
                errorInformation.setErrorType(SIGNUP_REQUEST_PASSWORD.getErrorType());
                errorInformation.setErrorTitle(SIGNUP_REQUEST_PASSWORD.getErrorTitle());
                errorInformation.setErrorDetail(SIGNUP_REQUEST_PASSWORD.getErrormessage());
                threadLocal.set(errorInformation);
                return new ResponseEntity<>(SIGNUP_REQUEST_PASSWORD.getHttpStatus());
        }
        throw new RuntimeException();
    }

}
