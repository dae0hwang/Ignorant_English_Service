package hello.api.controller;

import hello.api.dto.UserSentenceRequest;
import hello.api.service.SentenceAlarmKafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/sentence")
public class UserSentenceController {

    private final SentenceAlarmKafkaService sentenceAlarmKafkaService;

    //1 테이블 목록=그룹 등록 등록하기
    //완성
    @PostMapping("/add/group")
    public ResponseEntity<Void> addGroup(@RequestBody UserSentenceRequest request) {
        System.out.println(request.toString());
        sentenceAlarmKafkaService.sendCreateTableMessage(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //여기부터 테스트
    //2 문장 구독하기
    //보류
    @PostMapping("/add/subscribe")
    public ResponseEntity<Void> addSubscribe(@RequestBody UserSentenceRequest request) {
//        userSentenceService.subscribeSentence(request);
        sentenceAlarmKafkaService.sendAddSubscribeMessage(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    //3 문장 추가
    @PostMapping("/add/sentence")
    public ResponseEntity<Void> addSentence(@RequestBody UserSentenceRequest request) {
        sentenceAlarmKafkaService.sendAddSentenceMessage(request);
//        userSentenceService.addSentence(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //4 문장 삭제
    @PostMapping("/delete/sentence")
    public ResponseEntity<Void> deleteSentence(@RequestBody UserSentenceRequest request) {
        sentenceAlarmKafkaService.sendDeleteSentenceMessage(request);
//        userSentenceService.deleteSentence(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //5 알림 통합 확인
    @PostMapping("/check/alarm")
    public ResponseEntity<Void> checkSubscribeAlarm(@RequestBody UserSentenceRequest request) {
//        userSentenceService.checkAlarm(request);
        sentenceAlarmKafkaService.sendCheckAlarmMessage(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    //알림 정보 가져오기도 그냥 여기서 처리해도 되긴한데 일단 봐서 ㅇㅇㅇ
}
