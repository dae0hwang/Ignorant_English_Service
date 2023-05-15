package hello.plusapi.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSentenceRequest {

    private Long providerId;
    private String sentenceName;
    private Long subscriberId;
    private Long subscribedSentenceId;
    private Long updateSentenceGroupId;
    private String Korean;
    private String english;
    private Long deleteSentenceId;
    private Long alarmId;
    private Long sentenceUserId;
    private Long alarmUserId;
}