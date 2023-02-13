package com.example.wero.core.myletter.application;

import com.example.wero.core.myletter.domain.MyLetterDTO;

public interface MyLetterEditor {
    // 편지 작성
    String createMyLetter(MyLetterDTO myLetterDTO);

    // 편지 삭제
    // 편지 삭제 버튼은 사용자가 이미 로그인 한 상태라 자신의 편지함만 볼 수 있음을 가정
    // -> userId, userPw를 확인할 필요가 없음
    // 편지 id가 혹시라도 겹칠 경우를 대비해 writerId도 같이 받음
    String deleteMyLetter();
}
