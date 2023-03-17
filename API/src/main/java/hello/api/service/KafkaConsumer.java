package hello.api.service;

import hello.api.dto.EmailDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

//    @KafkaListener(topics = "exam", groupId = "foo")
//    public void consume(EmailDto emailDto) throws IOException {
//        System.out.println(String.format("Consumed message : %s", emailDto));
//    }
}