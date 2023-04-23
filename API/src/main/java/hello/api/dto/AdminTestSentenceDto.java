package hello.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminTestSentenceDto {

    private Long sentenceId;
    private String korean;
    private String english;
    private String hint;
    private String grammar;
    private String situation;
    private String check;
}
