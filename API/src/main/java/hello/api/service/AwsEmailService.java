package hello.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile({"aws","local"})
public class AwsEmailService implements SqsEmailService{

    @Value("${cloud.aws.end-point.url}")
    private String endpoint;
    private final QueueMessagingTemplate queueMessagingTemplate;

    public void sendMessage(String email) {
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(email).build());
        log.info("전송 완료");
    }
}