package hello.plusapi.controller;

import hello.plusapi.dto.AlarmInfoDto;
import hello.plusapi.dto.UserSentenceRequest;
import hello.plusapi.service.UserSentenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/sentence")
public class UserSentenceController {

    private final UserSentenceService userSentenceService;

    //유저에 해당하는 모든 알림 가져오기
    @PostMapping("/get/list/my/alarm")
    public ResponseEntity<List<AlarmInfoDto>> getMyAlarmList(
        @RequestBody UserSentenceRequest request) {
        System.out.println("reqeust=" + request.toString());
        List<AlarmInfoDto> myAlarmList = userSentenceService.getMyAlarmList(request);
        return new ResponseEntity<>(myAlarmList, HttpStatus.OK);
    }

    //알림 삭제하기
    @Operation(summary = "client가 request한 alarm id를 삭제", requestBody = @RequestBody)
    @PostMapping("delete/alarm")
    public ResponseEntity<Void> deleteAlarm(
        @RequestBody(description = "alarmId만 사용") UserSentenceRequest request) {
        userSentenceService.deleteAlarm(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
