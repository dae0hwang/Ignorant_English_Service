package hello.api.controller;

import hello.api.dto.AlarmInfoDto;
import hello.api.dto.UserSentenceDto;
import hello.api.dto.UserSentenceRequest;
import hello.api.service.SentenceAlarmService;
import hello.api.service.UserSentenceService;
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

    private final SentenceAlarmService sentenceAlarmService;
    private final UserSentenceService userSentenceService;


    //해당 유저 관리하는 문장 그룹 가져오기
    @PostMapping("/get/list/group")
    public ResponseEntity<List<UserSentenceDto>> getSentenceGroupList(
        @RequestBody UserSentenceRequest request) {
        List<UserSentenceDto> userSentenceGroupList = userSentenceService.getUserSentenceGroupList(
            request);
        return new ResponseEntity<>(userSentenceGroupList, HttpStatus.OK);
    }

    //앞으로 구독할 유저 문장 그룹 리스트 가져오기
    @PostMapping("/get/list/subscribe/group")
    public ResponseEntity<List<UserSentenceDto>> getSentenceSubscribeGroup(
        @RequestBody UserSentenceRequest request) {
        //userId 제외한 문장목록
        List<UserSentenceDto> userSentenceGroupList =
            userSentenceService.getUserSentenceSubscribeGroupList(request);
        return new ResponseEntity<>(userSentenceGroupList, HttpStatus.OK);
    }

    //유저가 구독한 문장 그룹 리스트 가져오기
    @PostMapping("/get/list/my/subscribe/group")
    public ResponseEntity<List<UserSentenceDto>> getSentenceMySubscribeGroup(
        @RequestBody UserSentenceRequest request) {
        //userId가 포함된 subscribe문장 정보 가져오기
        List<UserSentenceDto> userSentenceGroupList =
            userSentenceService.getUserSubscribeGroupLIst(request);
        return new ResponseEntity<>(userSentenceGroupList, HttpStatus.OK);
    }

    //새로운 문장 그룹 등록하기
    @PostMapping("/add/group")
    public ResponseEntity<Void> addGroup(@RequestBody UserSentenceRequest request) {
        userSentenceService.addSentenceGroup(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //문장 구독하기 - 알람
    @PostMapping("/add/subscribe")
    public ResponseEntity<Void> addSubscribe(@RequestBody UserSentenceRequest request) {
        userSentenceService.subscribeSentence(request);
        sentenceAlarmService.subscribeSentence(request.getSubscriberId(),
            request.getSubscribedSentenceId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    //문장 추가 - 알람
    @PostMapping("/add/sentence")
    public ResponseEntity<Void> addSentence(@RequestBody UserSentenceRequest request) {
        sentenceAlarmService.addSentence(request.getUpdateSentenceGroupId());
        userSentenceService.addSentence(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //K문장 삭제 - 알람
    @PostMapping("/delete/sentence")
    public ResponseEntity<Void> deleteSentence(@RequestBody UserSentenceRequest request) {
        userSentenceService.deleteSentence(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/info/group")
    public ResponseEntity<UserSentenceDto> getSentenceGroupInfo(@RequestParam Long groupId) {
        UserSentenceDto sentenceGroupInfo = userSentenceService.getSentenceGroupInfo(groupId);
        return new ResponseEntity<>(sentenceGroupInfo, HttpStatus.OK);
    }

    @GetMapping("/get/group/sentence")
    public ResponseEntity<List<UserSentenceDto>> getGroupSentence(@RequestParam Long groupId) {
        List<UserSentenceDto> groupSentence = userSentenceService.getGroupSentence(groupId);
        return new ResponseEntity<>(groupSentence, HttpStatus.OK);
    }

    @PostMapping("/get/list/my/alarm")
    public ResponseEntity<List<AlarmInfoDto>> getMyAlarmList(
        @RequestBody UserSentenceRequest request) {
        List<AlarmInfoDto> myAlarmList = sentenceAlarmService.getMyAlarmList(request);
        return new ResponseEntity<>(myAlarmList, HttpStatus.OK);
    }

    //알림 삭제하기
    @PostMapping("delete/alarm")
    public ResponseEntity<Void> deleteAlarm(@RequestBody UserSentenceRequest request) {
        sentenceAlarmService.deleteAlarm(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
