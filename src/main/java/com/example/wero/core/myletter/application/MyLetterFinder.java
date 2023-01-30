package com.example.wero.core.myletter.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MyLetterFinder {
    // 편지 읽기 위한 사용자가 작성한 모든 편지를 찾는 함수 myLetterFindAll

    /**
     * writerId에 맞는 사용자가 작성한 편지를 조회
     * @param writerId
     * @return List<MyLetterDTO>
     */
    List<MyLetterDTO> myLetterFindAll(String writerId);

    /**
     * 세션에 저장된 사용자의 정보를 통해 사용자가 작성한 모든 편지 중에서 특정한 편지 찾기
     * @param myLetterId
     * @return
     */
    MyLetterDTO findMyLetter(String myLetterId);
}
