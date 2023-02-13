package com.example.wero.core.receiveduser.application;

import com.example.wero.core.websocket.domain.BackMessage;

public interface ReceivedUserEditor {
    String createReceiveUser();
    
    String deleteReceivedUser(String myLetterId);

}
