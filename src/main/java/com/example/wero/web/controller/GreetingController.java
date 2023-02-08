package com.example.wero.web.controller;

import com.example.wero.core.myletter.application.MyLetterManager;
import com.example.wero.core.websocket.Greeting;
import com.example.wero.core.websocket.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    private final MyLetterManager myLetterManager;

    public GreetingController(MyLetterManager myLetterManager) {
        this.myLetterManager = myLetterManager;
    }



    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(String myLetterId) throws Exception {
        String[] myLetterIds = myLetterId.substring(2,myLetterId.length()-2).split("\":\"");
        Thread.sleep(1000); // simulated delay
        return new Greeting("myLetter" + HtmlUtils.htmlEscape(myLetterManager.findMyLetter(myLetterIds[1]).getMyLetterContent() + "@@@"));
    }


    // @MessageMapping을 통해 실제 유저가 어떤 메세지를 전달할 endpoint가 된다.
    // @SendTo도 마찬가지로 client들의 구독 endpoint

}