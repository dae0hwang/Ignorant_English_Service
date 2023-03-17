package hello.api.service;

import hello.api.dto.KafkaEmailDto;
import hello.api.dto.StatisticDto;
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
    private static final String STATISTIC_TOPIC = "statistic";
    private final KafkaTemplate<String, KafkaEmailDto> emailTemplate;
//    private final KafkaTemplate<String, StatisticDto> statisticTemplate;

    public void sendEmailMessage(String email) {
        KafkaEmailDto kafkaEmailDto = new KafkaEmailDto(email);
        log.info("produce email = {}", kafkaEmailDto);
        emailTemplate.send(EMAIL_TOPIC, kafkaEmailDto);
    }

    @KafkaListener(topics = "email", groupId = "foo")
    public void consume(KafkaEmailDto emailDto) throws IOException {
        log.info("consume email={}", emailDto);
    }

//    public void sendStatistic(String str) {
//        StatisticDto statisticDto = new StatisticDto(str);
//        log.info("produce statistic = {}", statisticDto);
//        statisticTemplate.send(STATISTIC_TOPIC, statisticDto);
//    }

//    @KafkaListener(topics = "statistic", groupId = "foo")
//    public void consume(StatisticDto statisticDto) throws IOException {
//        log.info("consume statistic={}", statisticDto);
//    }
}
