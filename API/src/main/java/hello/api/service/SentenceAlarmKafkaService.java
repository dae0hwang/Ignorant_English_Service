package hello.api.service;

import hello.api.dto.UserSentenceKafkaDto;
import hello.api.dto.UserSentenceRequest;
import hello.api.entity.SentenceSubscribe;
import hello.api.enumforkafaka.KafkaTopicEnum;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SentenceAlarmKafkaService {

    private final KafkaTemplate<String, UserSentenceKafkaDto> kafkaTemplate;

    public void sendAddSubscribeMessage(UserSentenceRequest request) {
        UserSentenceKafkaDto kafkaDto = UserSentenceKafkaDto.builder()
            .subscriberId(request.getSubscriberId()).subscribedSentenceId(
                request.getSubscribedSentenceId()).build();
        kafkaTemplate.send(KafkaTopicEnum.SUBSCRIBE.getStringTopic(), kafkaDto);
    }

    public void sendAddSentenceMessage(UserSentenceRequest request) {
        UserSentenceKafkaDto kafkaDto = UserSentenceKafkaDto.builder().updateSentenceGroupId(
            request.getUpdateSentenceGroupId()).Korean(request.getKorean()).english(
            request.getEnglish()).build();
        kafkaTemplate.send(KafkaTopicEnum.ADDSENTECE.getStringTopic(), kafkaDto);
    }

    public void sendDeleteSentenceMessage(UserSentenceKafkaDto kafkaDto) {
        kafkaTemplate.send(KafkaTopicEnum.DELETESENTENCE.getStringTopic(), kafkaDto);
    }
}
