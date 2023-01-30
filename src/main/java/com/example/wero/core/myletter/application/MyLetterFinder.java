package com.example.wero.core.myletter.application;

import com.example.wero.core.myletter.domain.MyLetterDTO;

import java.util.List;

public interface MyLetterFinder {
    List<MyLetterDTO> findAll();

    MyLetterDTO findLetter(String letterId);
}
