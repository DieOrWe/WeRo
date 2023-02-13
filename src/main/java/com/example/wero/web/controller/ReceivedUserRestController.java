package com.example.wero.web.controller;


import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.receiveduser.application.ReceivedUserEditor;
import com.example.wero.core.receiveduser.application.ReceivedUserFinder;
import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;

import com.example.wero.core.websocket.domain.BackMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
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
    public MyLetterDTO findReceivedLetter(String myLetterId) {
        return finder.findReceivedLetter(myLetterId);
    }

    //    @Scheduled(cron = "0/60 * * * * ?") // 시스템 시간을 기준으로 1분 마다 한 번씩 실행됨
    // Todo : 하루에 한 번 receivedUser 가 생성될 지, 시간을 기준으로 하루에 한 번 혹은 두 번 생성되게 할 지 정하기!!!
    @PostMapping(path = "createReceivedUser")
    public String createReceiveUser() {
        System.out.println("createReceiveUser() called");
        return editor.createReceiveUser();
    }
    
    @DeleteMapping(path = "deleteReceivedUser")
    public String deleteReceivedUser(String myLetterId) {
        return editor.deleteReceivedUser(myLetterId);
    }

}
