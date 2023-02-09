package com.example.wero.core.senduser.application;

import com.example.wero.core.myletter.domain.MyLetter;

public interface SendUserEditor {
    /**
     * MyLetterManager 에서 myLetter 가 생성될 때, 같이 생성
     *
     * @return SendUserDTO.getUserId()
     */
    String createUserLetter(MyLetter myLetter);

    /**
     * 편지의 Id를 받아서 sendUserRepository 에서 삭제
     *
     * @return String message (error: "편지 ID가 존재하지 않음", success: "삭제 성공")
     */
    String deleteUserLetter(String letterIds);
}
