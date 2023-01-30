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

    public MyLetterManager(MyLetterRepository myLetterRepository, ModelMapper modelMapper) {
        this.myLetterRepository = myLetterRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<MyLetterDTO> myLetterFindAll(String writerId) { // 일단 지금 myLetter를 전부 받아서 조건을 찾고 변환하는 것으로 구현
        // ToDo: MyLetterRepository에서 "Select * from MyLetters where writerId = writerId" 이런식으로 직접 받아오는 것 구현하기
        List<MyLetter> myLetters = myLetterRepository.findAll(); // repository에서 myLetter를 다 찾음
        List<MyLetter> filteredMyLetter = myLetters.stream() 
                .filter(a -> a.getWriterId().equals(writerId))
                .collect(Collectors.toList()); // 찾은 myLetter에서 writerId가 파라미터로 받은 것과 일치하는 것만 리스트로 다시 만듦
        // modelMapper를 통해 MyLetter entity를 DTO로 바꿔서 return 해줌
        return filteredMyLetter.stream().map(p -> modelMapper.map(p,MyLetterDTO.class)).collect(Collectors.toList()); 
    }

    @Override
    public MyLetterDTO findMyLetter(String myLetterId) {
        String message = String.format("%s에 해당하는 MyLetter가 없습니다.", myLetterId);
        MyLetter myLetter = myLetterRepository.findById(myLetterId).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(myLetter, MyLetterDTO.class);
    }
}
