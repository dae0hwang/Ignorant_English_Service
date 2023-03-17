package hello.api.service;

//import hello.api.dto.EmailDto;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//@Slf4j
//public class KafkaConsumer {
//
////    @KafkaListener(topics = "exam", groupId = "foo")
////    public void consume(EmailDto emailDto) throws IOException {
////        System.out.println(String.format("Consumed message : %s", emailDto));
////    }
//
//    @KafkaListener(topics = "newexam", groupId = "foo", containerFactory = "stockChangeListener")
//    public void consume(EmailDto emailDto) {
//
//        log.info("email consume={}", emailDto);
//    }
//}

import hello.api.dto.StockChange;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "aa", groupId = "foo", containerFactory = "stockChangeListener")
    public void consume(StockChange stockChange) {
        System.out.printf("Consumed message : %s%n", stockChange.getYyyymmdd());
    }
}