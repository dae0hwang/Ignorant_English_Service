package hello.plusapi.controller;

import hello.plusapi.dto.UserSentenceDto;
import hello.plusapi.dto.UserSentenceRequest;
import hello.plusapi.service.UserSentenceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/sentence")
public class UserSentenceController {

    private final UserSentenceService userSentenceService;

    @PostMapping("/get/list/group")
    public ResponseEntity<List<UserSentenceDto>> getSentenceGroupList(
        @RequestBody UserSentenceRequest request) {
        System.out.println(request.toString());
        List<UserSentenceDto> userSentenceGroupList = userSentenceService.getUserSentenceGroupList(
            request);
        return new ResponseEntity<>(userSentenceGroupList, HttpStatus.OK);
    }

    @GetMapping("/get/info/group")
    public ResponseEntity<UserSentenceDto> getSentenceGroupInfo(@RequestParam Long groupId) {
        UserSentenceDto sentenceGroupInfo = userSentenceService.getSentenceGroupInfo(groupId);
        return new ResponseEntity<>(sentenceGroupInfo, HttpStatus.OK);
    }

    //그룹아이디로 문장 목록 전체 가져오기
    @GetMapping("/get/group/sentence")
    public ResponseEntity<List<UserSentenceDto>> getGroupSentence(@RequestParam Long groupId) {
        List<UserSentenceDto> groupSentence = userSentenceService.getGroupSentence(groupId);
        return new ResponseEntity<>(groupSentence, HttpStatus.OK);
    }


    //문장 목록 추가하기가 있다. 이름 지정해서
    //이건 완성
    //1 테이블 목록=그룹 등록
//    @PostMapping("/add/group")
//    public ResponseEntity<Void> addGroup(@RequestBody UserSentenceRequest request) {
//        userSentenceService.addSentenceGroup(request);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    //여기다가 문장 구독하자 ㅇㅇ
    //이것은
    //2 문장 구독하기
//    @PostMapping("/add/subscribe")
//    public ResponseEntity<Void> addSubscribe(@RequestBody UserSentenceRequest request) {
//        userSentenceService.subscribeSentence(request);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    //3 문장 추가
//    @PostMapping("/add/sentence")
//    public ResponseEntity<Void> addSentence(@RequestBody UserSentenceRequest request) {
//        userSentenceService.addSentence(request);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    //4 문장 삭제
//    @PostMapping("/delete/sentence")
//    public ResponseEntity<Void> deleteSentence(@RequestBody UserSentenceRequest request) {
//        userSentenceService.deleteSentence(request);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    //5 알림 통합 확인
//    @PostMapping("/check/alarm")
//    public ResponseEntity<Void> checkSubscribeAlarm(@RequestBody UserSentenceRequest request) {
//        userSentenceService.checkAlarm(request);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
