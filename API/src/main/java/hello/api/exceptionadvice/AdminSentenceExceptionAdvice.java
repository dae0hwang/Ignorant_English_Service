package hello.api.exceptionadvice;

import static hello.api.enumforexception.AdminSentenceExceptionEnum.ADD_SENTENCE_STRING_BLANK;
import static hello.api.enumforexception.AdminSentenceExceptionEnum.NO_MATCH_GRAMMAR_ENUM;
import static hello.api.enumforexception.AdminSentenceExceptionEnum.NO_MATCH_SITUATION_ENUM;
import static hello.api.enumforexception.AdminSentenceExceptionEnum.findByErrorMessage;

import hello.api.controller.AdminSentenceController;
import hello.api.exception.AdminSentenceException;
import hello.api.exception.ErrorInformationTlsException;
import hello.api.threadlocalstorage.ErrorInformation;
import hello.api.threadlocalstorage.ErrorInformationTlsContainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = {AdminSentenceController.class})
public class AdminSentenceExceptionAdvice {

    private final ErrorInformationTlsContainer errorInformationTlsContainer;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidExceptionAdvice(MethodArgumentNotValidException e) {
        log.warn("[exceptionAdvice] ex", e);
        return makeArgumentNotValidResponseAndSetTls(e);
    }

    @ExceptionHandler(AdminSentenceException.class)
    public ResponseEntity companyFoodExceptionAdvice(AdminSentenceException e) {
        log.warn("[exceptionAdvice] ex", e);
        return makeCompanyFoodExceptionResponseAndSetTls(e);
    }

    private ResponseEntity makeCompanyFoodExceptionResponseAndSetTls(
        AdminSentenceException e) {
        ThreadLocal<ErrorInformation> threadLocal = errorInformationTlsContainer.getThreadLocal();
        if (!threadLocal.get().getErrorTitle().equals("none")) {
            throw new ErrorInformationTlsException("errorInformationTls is not Empty");
        }

        String errorMessage = e.getMessage();
        ErrorInformation errorInformation = new ErrorInformation();
        switch (findByErrorMessage(errorMessage)) {
            case NO_MATCH_GRAMMAR_ENUM:
                errorInformation.setErrorType(NO_MATCH_GRAMMAR_ENUM.getErrorType());
                errorInformation.setErrorTitle(NO_MATCH_GRAMMAR_ENUM.getErrorTitle());
                errorInformation.setErrorDetail(NO_MATCH_GRAMMAR_ENUM.getErrormessage());
                threadLocal.set(errorInformation);
                return new ResponseEntity(NO_MATCH_GRAMMAR_ENUM.getHttpStatus());
            case NO_MATCH_SITUATION_ENUM:
                errorInformation.setErrorType(NO_MATCH_SITUATION_ENUM.getErrorType());
                errorInformation.setErrorTitle(NO_MATCH_SITUATION_ENUM.getErrorTitle());
                errorInformation.setErrorDetail(NO_MATCH_SITUATION_ENUM.getErrormessage());
                threadLocal.set(errorInformation);
                return new ResponseEntity(NO_MATCH_SITUATION_ENUM.getHttpStatus());
        }
        throw new RuntimeException();
    }

    private ResponseEntity makeArgumentNotValidResponseAndSetTls(
        MethodArgumentNotValidException e) {
        ThreadLocal<ErrorInformation> threadLocal = errorInformationTlsContainer.getThreadLocal();
        if (!threadLocal.get().getErrorTitle().equals("none")) {
            throw new ErrorInformationTlsException("errorInformationTls is not Empty");
        }
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorInformation errorInformation = new ErrorInformation();
        switch (findByErrorMessage(errorMessage)) {
            case ADD_SENTENCE_STRING_BLANK:
                errorInformation.setErrorType(ADD_SENTENCE_STRING_BLANK.getErrorType());
                errorInformation.setErrorTitle(ADD_SENTENCE_STRING_BLANK.getErrorTitle());
                errorInformation.setErrorDetail(ADD_SENTENCE_STRING_BLANK.getErrormessage());
                threadLocal.set(errorInformation);
                return new ResponseEntity(ADD_SENTENCE_STRING_BLANK.getHttpStatus());
        }
        throw new RuntimeException();
    }

}
