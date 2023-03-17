//package hello.api.controller;
//
//import hello.api.service.KafkaEmailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value = "/kafka")
//public class KafkaController {
//    private final KafkaEmailService kafkaEmailService;
//
//    @PostMapping
//    public String sendMessage(@RequestParam("message") String message) {
//        kafkaEmailService.sendMessage(message);
//        return "success";
//    }
//}