package hello.api.exceptionadvice;

import static hello.api.enumforexception.AdminSentenceExceptionEnum.ADD_SENTENCE_STRING_BLANK;
import static hello.api.enumforexception.AdminSentenceExceptionEnum.NO_MATCH_GRAMMAR_ENUM;
import static hello.api.enumforexception.AdminSentenceExceptionEnum.NO_MATCH_SITUATION_ENUM;
import static hello.api.enumforexception.AdminSentenceExceptionEnum.findByErrorMessage;

import hello.api.controller.AdminSentenceController;
import hello.api.exception.AdminSentenceException;
import hello.api.threadlocalstorage.ErrorInformationTls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(assignableTypes = {AdminSentenceController.class})
public class AdminSentenceExceptionAdvice {

    private final ErrorInformationTls threadLocalStorage = new ErrorInformationTls();

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
        String errorMessage = e.getMessage();
        switch (findByErrorMessage(errorMessage)) {
            case NO_MATCH_GRAMMAR_ENUM:
                threadLocalStorage.setErrorType(NO_MATCH_GRAMMAR_ENUM.getErrorType());
                threadLocalStorage.setErrorTitle(NO_MATCH_GRAMMAR_ENUM.getErrorTitle());
                threadLocalStorage.setErrorDetail(NO_MATCH_GRAMMAR_ENUM.getErrormessage());
                return new ResponseEntity(NO_MATCH_GRAMMAR_ENUM.getHttpStatus());
            case NO_MATCH_SITUATION_ENUM:
                threadLocalStorage.setErrorType(NO_MATCH_SITUATION_ENUM.getErrorType());
                threadLocalStorage.setErrorTitle(NO_MATCH_SITUATION_ENUM.getErrorTitle());
                threadLocalStorage.setErrorDetail(NO_MATCH_SITUATION_ENUM.getErrormessage());
                return new ResponseEntity(NO_MATCH_SITUATION_ENUM.getHttpStatus());
        }
        throw new RuntimeException();
    }

    private ResponseEntity makeArgumentNotValidResponseAndSetTls(
        MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        switch (findByErrorMessage(errorMessage)) {
            case ADD_SENTENCE_STRING_BLANK:
                threadLocalStorage.setErrorType(ADD_SENTENCE_STRING_BLANK.getErrorType());
                threadLocalStorage.setErrorTitle(ADD_SENTENCE_STRING_BLANK.getErrorTitle());
                threadLocalStorage.setErrorDetail(ADD_SENTENCE_STRING_BLANK.getErrormessage());
                return new ResponseEntity(ADD_SENTENCE_STRING_BLANK.getHttpStatus());
        }
        throw new RuntimeException();
    }

}
