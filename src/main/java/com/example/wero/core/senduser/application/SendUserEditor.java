package com.example.wero.core.senduser.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.senduser.domain.SendUser;
import com.example.wero.core.senduser.domain.SendUserDTO;

public interface SendUserEditor {
    /**
     * MyLetterManager에서 myLetter가 생성될 때, 같이 생성
     * @param myLetter
     * @return SendUserDTO.getUserId()
     */
    String createUserLetter (MyLetter myLetter);

    /**
     * 편지의 Id를 받아서 myLetterRepository에서 삭제
     * myLetterReopository에서 삭제되면서 senUserRepository도 같이 삭제되니깐
     * @param letterId
     * @return String message (error: "편지 ID가 존재하지 않음", success: "삭제 성공")
     */
    String deleteUserLetter (String letterId);
}
