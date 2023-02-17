package hello.api.enumforexception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@Slf4j
public enum AdminSentenceExceptionEnum {
    ADD_SENTENCE_STRING_BLANK(Constants.addSentenceStringBlank,
        "addSentenceRequestValidationException", "/errors/admin/sentence/add/string-blank",
        HttpStatus.BAD_REQUEST),
    NO_MATCH_GRAMMAR_ENUM(Constants.noMatchGrammarEnum, "noMatchGrammarEnumException",
        "/errors/admin/sentence/add/no-match-enum", HttpStatus.BAD_REQUEST),
    NO_MATCH_SITUATION_ENUM(Constants.noMatchSituationEnum, "noMatchSituationException",
        "/errors/admin/sentence/add/no-match-enum", HttpStatus.BAD_REQUEST),
    ;

    private final String errormessage;
    private final String errorTitle;
    private final String errorType;
    private final HttpStatus httpStatus;

    public static AdminSentenceExceptionEnum findByErrorMessage(String errormessage) {
        for (AdminSentenceExceptionEnum exceptionEnum : AdminSentenceExceptionEnum.values()) {
            if (exceptionEnum.getErrormessage().equals(errormessage)) {
                return exceptionEnum;
            }
        }
        throw new RuntimeException();
    }

    public static class Constants {
        public static final String addSentenceStringBlank =
            "addSentence request string can't be blank";
        public static final String noMatchGrammarEnum =
            "no match grammar enum";
        public static final String noMatchSituationEnum =
            "no match situation enum";

    }
}
