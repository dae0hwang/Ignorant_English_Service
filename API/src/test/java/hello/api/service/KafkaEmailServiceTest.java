//package hello.api.service;
//
//import static org.hamcrest.Matchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//
//import hello.api.dto.KafkaEmailDto;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.protocol.Message;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.Spy;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.concurrent.FailureCallback;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
//import org.springframework.util.concurrent.SuccessCallback;
//
//@SpringBootTest
//@Transactional
//@ActiveProfiles("test")
//class KafkaEmailServiceTest {
//    KafkaTemplate kafkaTemplate = Mockito.mock(KafkaTemplate.class);
//    KafkaEmailService kafkaEmailService;
//
//    @Test
//    void sendMessage() {
//        kafkaEmailService = new KafkaEmailService(kafkaTemplate);
//        when(kafkaTemplate.send(anyString(), any(Message.class))).thenReturn(null);
//        Assertions.assertThatThrownBy(() -> kafkaEmailService.sendMessage("email"))
//            .isInstanceOf(RuntimeException.class);
//    }
//}