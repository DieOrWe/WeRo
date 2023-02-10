package com.example.wero.web.controller;

import com.example.wero.core.websocket.application.WebSocketHandler;
import com.example.wero.core.websocket.domain.BackMessage;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;


// WebSocket 의 RestController
@RestController
public class WebSocketController {

    private final WebSocketHandler webSocketHandler;


    public WebSocketController(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

 
    @MessageMapping("/frontmessage")
    @SendTo("/topic/backmessage")
    public BackMessage backMessage(String myLetterId) throws Exception {
        return webSocketHandler.getLetterData(myLetterId);
    }


    // @MessageMapping 을 통해 실제 유저가 어떤 메세지를 전달할 endpoint 가 된다.
    // @SendTo도 마찬가지로 client 들의 구독 endpoint

    // RestController vs Controller

}