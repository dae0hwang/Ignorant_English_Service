package hello.api.service;

import hello.api.dto.EmailDto;
import hello.api.dto.StockChange;
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
    private final KafkaTemplate<String, EmailDto> kafkaTemplate;

    public void sendMessage(String message) {
        EmailDto emailDto = new EmailDto(message);
        log.info("email produce ={}", emailDto);
        kafkaTemplate.send(TOPIC, emailDto);
    }

    @KafkaListener(topics = TOPIC, groupId = "foo", containerFactory = "kafkaEmailListener")
    public void consume(EmailDto emailDto) {
        log.info("email consume ={}", emailDto);
    }
}