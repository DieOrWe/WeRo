package com.example.wero.core.senduser.application;

import com.example.wero.core.senduser.domain.SendUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SendUserFinder {
    List<SendUserDTO> findSendLetters(String userId);
}
