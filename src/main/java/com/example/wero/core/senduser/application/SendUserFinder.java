package com.example.wero.core.senduser.application;

import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.senduser.domain.SendUserDTO;

import java.util.List;


public interface SendUserFinder {
    /**
     * 보낸 편지함(FE)에서 편지들 목록을 확인하기 위한 메소드
     *
     * @param userId: 요청을 보낸 해당 사용자의 ID가 파라미터
     * @return List<SendUserDTO>: 회원 ID, 편지 ID, 편지 제목, 작성 일자를 보여줌
     */
    List<SendUserDTO> findAllMySendLetters(String userId);

    /**
     * 보낸 편지함에서 편지를 클릭했을 때, 해당 편지의 상세 정보를 보여주기 위한 메소드
     *
     * @param myLetterId: 편지함에서 편지를 클릭하면 myLetterId를 파라미터로 보내줘야 함.
     * @return myLetterDTO
     */
    MyLetterDTO findSendLetter(String myLetterId);
}
