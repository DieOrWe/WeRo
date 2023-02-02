package com.example.wero.web.controller;



import com.example.wero.core.receiveduser.application.ReceivedUserEditor;
import com.example.wero.core.receiveduser.application.ReceivedUserFinder;
import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/user/myLetters")
public class ReceivedUserController {
    private final ReceivedUserFinder finder;
    private final ReceivedUserEditor editor;
    
    public ReceivedUserController(ReceivedUserFinder finder, ReceivedUserEditor editor) {
        this.finder = finder;
        this.editor = editor;
    }
    @PostMapping
    public ReceivedUserDTO showMyAllLetters(@RequestBody String userId) {
        return finder.findMyAllLetter(userId);
    }
}
