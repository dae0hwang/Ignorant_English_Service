package hello.api.controller;

import hello.api.service.SqsEmailService;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.core.env.Environment;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HelmTest {


//    @Value("${cloud.aws.end-point.url}")
//    private String endpoint;
//    private final SqsEmailService sqsEmailService;
//
//    @GetMapping("/send/{message}")
//    public String sendMessage(@PathVariable String message, HttpServletRequest request) {
//        sqsEmailService.sendMessage(message);
////        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
//        log.info(request.getRequestURL().substring(7).replace(request.getRequestURI(),""));
//        return "ok";
//    }

    @GetMapping("/helm")
    public String redis() {
        return "first";
    }
}
