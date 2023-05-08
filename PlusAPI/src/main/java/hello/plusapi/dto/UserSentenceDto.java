package hello.plusapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSentenceDto {

    private Long sentenceGroupId;
    private String sentenceGroupName;

    private Long sentenceId;
    private String korean;
    private String english;
}
