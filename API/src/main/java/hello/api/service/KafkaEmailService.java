package hello.api.service;

import hello.api.dto.EmailDto;
import hello.api.dto.StockChange;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEmailService {

    private static final String TOPIC = "email";
    private final KafkaTemplate<String, EmailDto> kafkaTemplate;

    public void sendMessage(String message) {
        EmailDto emailDto = new EmailDto(message);
        kafkaTemplate.send(TOPIC, emailDto);
    }

    @KafkaListener(topics = TOPIC, groupId = "foo", containerFactory = "kafkaEmailListener")
    public void consume(StockChange stockChange) {
        System.out.printf("Consumed message : %s%n", stockChange.getYyyymmdd());
    }
}