package com.example.wero.core.myletter.application;

import com.example.wero.core.myletter.domain.MyLetterDTO;

public interface MyLetterEditor {

    String createMyLetter(MyLetterDTO newLetter);

    String deleteMyLetter(String myletterId);
}
