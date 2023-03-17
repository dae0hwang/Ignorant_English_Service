package hello.api.service;
import hello.api.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {
    private static final String TOPIC = "newexam";
    private final KafkaTemplate<String, EmailDto> kafkaTemplate;
//    private final KafkaTemplate<String, Object> kafkaTemplate;
//
//    public void sendMessage(String message) {
//        EmailDto emailDto = new EmailDto(message);
//        System.out.println(String.format("Produce message : %s", emailDto));
//        this.kafkaTemplate.send(TOPIC, emailDto);
//    }

    public void sendMessage(String message) {
        EmailDto emailDto = new EmailDto(message);
        // Send a message
        log.info("produce message={}", emailDto);
        kafkaTemplate.send(TOPIC, emailDto);
    }
}