package hello.api.service;

import hello.api.dto.KafkaEmailDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaService {

    private static final String EMAIL_TOPIC = "email";
    private final KafkaTemplate<String, KafkaEmailDto> emailTemplate;

    public void sendEmailMessage(String email) {
        emailTemplate.send(EMAIL_TOPIC, new KafkaEmailDto(email));
    }
//
//    @KafkaListener(topics = "email", groupId = "foo")
//    public void consume(KafkaEmailDto emailDto) throws IOException {
//        log.info("kafka test={}", emailDto.getEmail());
//    }
}
