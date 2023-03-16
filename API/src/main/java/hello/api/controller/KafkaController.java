package hello.api.controller;

import hello.api.service.KafkaProducer;
import hello.api.service.KafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/kafka")
public class KafkaController {
    private final KafkaProducer producer;
    private final KafkaService kafkaService;

    @PostMapping
    public String sendEmail(@RequestParam("message") String message) {
        kafkaService.sendEmailMessage(message);
        return "success";
    }

}