package hello.api.service;

import hello.api.dto.KafkaEmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile({ "server", "test"})
public class KafkaEmailService implements SqsEmailService{

    private static final String TOPIC = "email";
    private final KafkaTemplate<String, KafkaEmailDto> kafkaTemplate;

    public void sendMessage(String email) {
        KafkaEmailDto kafkaEmailDto = new KafkaEmailDto(email);
        log.info("kafka produce email message ={}", kafkaEmailDto);
        kafkaTemplate.send(TOPIC, kafkaEmailDto);
    }
}