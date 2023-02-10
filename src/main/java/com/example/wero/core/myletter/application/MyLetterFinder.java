package com.example.wero.core.myletter.application;

import com.example.wero.core.myletter.domain.MyLetterDTO;

public interface MyLetterFinder {
    /**
     * 세션에 저장된 사용자의 정보를 통해 사용자가 작성한 모든 편지 중에서 특정한 편지 찾기
     */
    MyLetterDTO findMyLetter(String myLetterId);
}
