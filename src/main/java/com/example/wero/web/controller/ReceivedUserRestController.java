package com.example.wero.web.controller;


import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.receiveduser.application.ReceivedUserEditor;
import com.example.wero.core.receiveduser.application.ReceivedUserFinder;
import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;

import com.example.wero.core.websocket.domain.BackMessage;
import io.swagger.annotations.Api;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user/myLetters")
public class ReceivedUserRestController {

    private final ReceivedUserFinder finder;
    private final ReceivedUserEditor editor;

    public ReceivedUserRestController(ReceivedUserFinder finder, ReceivedUserEditor editor) {
        this.finder = finder;
        this.editor = editor;
    }

    @PostMapping(path = "/{userId}")
    public List<ReceivedUserDTO> findAllMyReceivedLetters(@PathVariable String userId) {
        return finder.findAllMyReceivedLetters(userId);
    }

    @PostMapping(path = "/received")
    public MyLetterDTO findReceivedLetter(@RequestBody String myLetterId) {
        return finder.findReceivedLetter(myLetterId);
    }

//    @Scheduled(cron = "* * 00,12 * * ?") // 시스템 시간을 기준으로 매일 00시와 12시 00분 00초에 실행됨.
//    @PostMapping(path = "/createReceivedUser")
    @Scheduled(cron = "0 * * * * *") // 배포 test용 1분마다 실행되게 함.
    public String createReceiveUser() {
        System.out.println("createReceiveUser() called");
        return editor.createReceiveUser();
    }
    
    
    @DeleteMapping(path = "/deleteReceivedUser")
    public String deleteReceivedUser(@RequestBody String myLetterId) {
        return editor.deleteReceivedUser(myLetterId);
    }

}
