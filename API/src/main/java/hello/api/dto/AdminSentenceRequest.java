package hello.api.dto;

import hello.api.enumforexception.AdminSentenceExceptionEnum.Constants;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminSentenceRequest {

    @NotBlank(message = Constants.addSentenceStringBlank)
    private String korean;
    @NotBlank(message = Constants.addSentenceStringBlank)
    private String english;
    private String grammar;
    private String situation;
}
