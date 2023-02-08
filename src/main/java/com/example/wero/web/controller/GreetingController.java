package com.example.wero.web.controller;

import com.example.wero.core.websocket.Greeting;
import com.example.wero.core.websocket.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }


    // @MessageMapping을 통해 실제 유저가 어떤 메세지를 전달할 endpoint가 된다.
    // @SendTo도 마찬가지로 client들의 구독 endpoint

}