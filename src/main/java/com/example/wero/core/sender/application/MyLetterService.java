package com.example.wero.core.sender.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.receiveduser.domain.ReceivedUser;
import com.example.wero.core.receiveduser.infrastructure.ReceivedUserRepository;
import com.example.wero.core.senduser.domain.SendUser;
import com.example.wero.core.senduser.infrastructure.SendUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyLetterService {

    @Autowired
    private SendUserRepository sendUserRepository;

    @Autowired
    private ReceivedUserRepository receiveUserRepository;
// 논리에 문제 발견
//    public void sendMyLetter(int sendUserId, String receivedUserId) {
//        SendUser sendUser = sendUserRepository.findById(sendUserId).orElseThrow(() -> new RuntimeException("SendUser를 못찾았습니다"));
//        ReceivedUser receivedUser = receiveUserRepository.findById(receivedUserId).orElseThrow(() -> new RuntimeException("ReceivedUser를 못 찾았습니다."));
//
//        String myLetterId = sendUser.getMyLetterId();
//        receivedUser.setMyLetterId(myLetterId);
//
//        sendUserRepository.save(sendUser);
//        receiveUserRepository.save(receivedUser);
//    }

    public void sendMyLetter(){
        
    }
}
