package hello.plusapi.dto;

import hello.plusapi.entity.SentenceGroup;
import hello.plusapi.entity.SentenceSubscribe;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSentenceKafkaDto {

    private Long providerId;
    private String sentenceName;
    private Long subscriberId;
    private Long subscribedSentenceId;
    private Long updateSentenceGroupId;
    private String Korean;
    private String english;
    private Long deleteSentenceId;
    private Long alarmId;
    private List<SentenceSubscribe> list;
    private SentenceGroup sentenceGroup;
}
