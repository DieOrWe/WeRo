package com.example.wero.core.senduser.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.senduser.domain.SendUserDTO;

public interface SendUserEditor {
    SendUserDTO createUserLetter (MyLetterDTO myLetterDTO);
}
