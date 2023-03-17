package hello.api.service;
import hello.api.dto.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private static final String TOPIC = "exam";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String message) {
        EmailDto emailDto = new EmailDto(message);
        System.out.println(String.format("Produce message : %s", emailDto));
        this.kafkaTemplate.send(TOPIC, emailDto);
    }
}