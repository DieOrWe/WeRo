package com.example.wero.web.controller;

import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.senduser.application.SendUserEditor;
import com.example.wero.core.senduser.application.SendUserFinder;
import com.example.wero.core.senduser.domain.SendUserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/user/sendLetters")
public class SendUserRestController {
    private final SendUserFinder finder;
    private final SendUserEditor editor;

    public SendUserRestController(SendUserFinder finder, SendUserEditor editor) {
        this.finder = finder;
        this.editor = editor;
    }

    @PostMapping(path = "/{userId}")
    public List<SendUserDTO> findAllMySendLetters(@PathVariable String userId) {
        return finder.findAllMySendLetters(userId);
    }

    @PostMapping(path = "/check")
    public MyLetterDTO findMySendLetter(@RequestBody String myLetterId) {
        return finder.findSendLetter(myLetterId);
    }

    @DeleteMapping
    public String deleteSendLetter(@RequestBody String myLetterIds) {
        return editor.deleteUserLetter(myLetterIds);
    }
}
