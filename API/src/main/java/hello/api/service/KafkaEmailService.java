package hello.api.service;

import hello.api.dto.KafkaEmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaEmailService {

    private static final String TOPIC = "newemail";
    private final KafkaTemplate<String, KafkaEmailDto> kafkaTemplate;

    public void sendMessage(String message) {
        KafkaEmailDto kafkaEmailDto = new KafkaEmailDto(message);
        log.info("email produce ={}", kafkaEmailDto);
        kafkaTemplate.send(TOPIC, kafkaEmailDto);
    }

    @KafkaListener(topics = TOPIC, groupId = "foo", containerFactory = "kafkaEmailListener")
    public void consume(KafkaEmailDto kafkaEmailDto) {
        log.info("email consume ={}", kafkaEmailDto);
    }
}