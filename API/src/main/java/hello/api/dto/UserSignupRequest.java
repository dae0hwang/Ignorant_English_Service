package hello.api.dto;

import hello.api.annotaion.Password;
import hello.api.enumforexception.UserManageExceptionEnum.Constants;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignupRequest {
    @Email(message = Constants.signupRequestEmail)
    private String username;
    @Password
    private String password;
    @NotBlank(message = Constants.signupRequestName)
    private String name;
}
