package com.example.wero.core.myletter.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MyLetterManager implements MyLetterFinder, MyLetterEditor {

    private final MyLetterRepository myLetterRepository;

    private final ModelMapper modelMapper;

    public MyLetterManager(MyLetterRepository myLetterRepository, ModelMapper modelMapper){
        this.myLetterRepository = myLetterRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MyLetterDTO> findAll() {
        List<MyLetter> letterEntity = myLetterRepository.findAll();
        return letterEntity.stream().map(p -> modelMapper.map(p, MyLetterDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MyLetterDTO findLetter(String letterId){
        String message = String.format("%s에 해당하는 편지가 없습니다.", letterId);
        MyLetter myLetter = myLetterRepository.findById(letterId).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(myLetter, MyLetterDTO.class);
    }

    @Override
    public String createMyLetter(MyLetterDTO newLetter) {
        return null;
    }

    @Override
    public String deleteMyLetter(String myletterId) {
        return null;
    }
}
