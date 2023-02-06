package com.example.wero.core.sender.application;

import com.example.wero.core.myletter.application.MyLetterFinder;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import com.example.wero.core.receiveduser.application.ReceivedUserEditor;
import com.example.wero.core.receiveduser.application.ReceivedUserFinder;
import com.example.wero.core.senduser.application.SendUserEditor;
import com.example.wero.core.senduser.application.SendUserFinder;
import com.example.wero.core.senduser.domain.SendUser;
import com.example.wero.core.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SenderService implements MyLetterFinder {
    private final MyLetterRepository myLetterRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final SendUserFinder sendUserFinder;
    private final ReceivedUserFinder receivedUserFinder;

    public SenderService(MyLetterRepository myLetterRepository, ModelMapper modelMapper, UserRepository userRepository, SendUserFinder sendUserFinder, ReceivedUserFinder receivedUserFinder) {
        this.myLetterRepository = myLetterRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.sendUserFinder = sendUserFinder;
        this.receivedUserFinder = receivedUserFinder;
    }

    public SendLetter sendLetter(String myLetterId) {

    }


}
