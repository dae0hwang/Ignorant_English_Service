package hello.plusapi.dto;

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


    //유저가 가지고 있는 문장 그룹 전체 정보 아이디랑, 그룹이름 가져오기
    private Long sentenceUserId;

    private Long AlarmUserId;
}