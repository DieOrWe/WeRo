package com.example.wero.web.controller;


import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import com.example.wero.core.receiveduser.application.ReceivedUserFinder;
import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/user/myLetters")
public class ReceivedUserController {
    private final ReceivedUserFinder finder;
    private final MyLetterRepository myLetterRepository;

    public ReceivedUserController(ReceivedUserFinder finder,
                                  MyLetterRepository myLetterRepository){
        this.finder = finder;
        this.myLetterRepository = myLetterRepository;
    }

    @PostMapping(path = "/{userId}")
    public List<ReceivedUserDTO> findAllMyReceivedLetters(@PathVariable String userId){
        return finder.findAllMyReceivedLetters(userId);
    }

    @PostMapping(path = "/received")
    public MyLetterDTO findReceivedLetter(String myLetterId){
        return finder.findReceivedLetter(myLetterId);
    }
}
