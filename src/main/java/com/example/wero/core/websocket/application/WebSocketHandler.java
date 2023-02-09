package com.example.wero.core.websocket.application;

import com.example.wero.core.myletter.application.MyLetterManager;
import com.example.wero.core.websocket.domain.BackMessage;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.HtmlUtils;


// Service Web socket 라고 생각하면 편함.
@Service
public class WebSocketHandler extends TextWebSocketHandler {

    private final MyLetterManager myLetterManager;

    public WebSocketHandler(MyLetterManager myLetterManager) {
        this.myLetterManager = myLetterManager;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        System.out.println(session.toString());
        System.out.println(message.toString());

    }
 
    // handleTextMessage 는 session 과 메세지를 단순히 출력해주는 메서드

    public BackMessage getLetterData(String myLetterId) throws Exception {
        Thread.sleep(1000); // simulated delay
        String[] myLetterIds = myLetterId.substring(2, myLetterId.length() - 2).split("\":\"");
        return new BackMessage("myLetterContents :  " + HtmlUtils.htmlEscape(myLetterManager.findMyLetter(myLetterIds[1]).getMyLetterContent()));
    }

    // getLetterData 를 통해 myLetterId 를 통한 편지 조회 및 본문가져와서 보여주기

}