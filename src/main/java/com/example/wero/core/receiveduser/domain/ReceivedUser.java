package com.example.wero.core.receiveduser.domain;

import com.example.wero.core.user.domain.User;

import javax.persistence.Id;

public class ReceivedUser {
    @Id
    private String userId; // 회원ID
    
    private String myLetterId; // 편지 ID
    
    private boolean isRead;
    
    
    
}
