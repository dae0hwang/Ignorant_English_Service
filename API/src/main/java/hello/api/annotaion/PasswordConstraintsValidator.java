package hello.api.annotaion;

import hello.api.enumforexception.UserManageExceptionEnum;
import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintsValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator passwordValidator = new PasswordValidator(
            Arrays.asList(
                //Length rule. Min 8 max 128 characters
                new LengthRule(8, 128),
                //At least one alphabet
                new CharacterRule(EnglishCharacterData.Alphabetical, 1),
                //At least one number
                new CharacterRule(EnglishCharacterData.Digit, 1),
                //At least one special characters
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
            )
        );
        RuleResult result = passwordValidator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        //Sending one message each time failed validation.
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                UserManageExceptionEnum.SIGNUP_REQUEST_PASSWORD.getErrormessage())
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return false;
    }
}
