package com.example.wero.core.myletter.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyLetterManager implements MyLetterFinder, MyLetterEditor {
    private final MyLetterRepository myLetterRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public MyLetterManager(MyLetterRepository myLetterRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.myLetterRepository = myLetterRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<MyLetterDTO> myLetterFindAll(String writerId) { // 일단 지금 myLetter를 전부 받아서 조건을 찾고 변환하는 것으로 구현
        // ToDo: MyLetterRepository에서 "Select * from MyLetters where userId = writerId" 이런식으로 직접 받아오는 것 구현하기
        List<MyLetter> myLetters = myLetterRepository.findByWriterId(writerId);
        System.out.println("writerId" + writerId);
        System.out.println(myLetterRepository.findAll());
        System.out.println(myLetters);
//        if(myLetters.isEmpty()) {
//            String message = "작성한 편지가 없습니다.";
//            return null;
//        }
        List<MyLetterDTO> foundLetters = myLetters.stream().map(myLetter -> modelMapper.map(myLetter, MyLetterDTO.class)).collect(Collectors.toList());
        System.out.println("---- foundLetters : " + foundLetters);

//        return myLetters.stream().map(p -> modelMapper.map(p,MyLetterDTO.class)).collect(Collectors.toList());
        return myLetters.stream().map(myLetter -> modelMapper.map(myLetter, MyLetterDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MyLetterDTO findMyLetter(String myLetterId) { // 쓰일 일이 있을 것 같아서 일단 구현
        String message = String.format("%s에 해당하는 MyLetter가 없습니다.", myLetterId);
        final MyLetter myLetter = myLetterRepository.findById(myLetterId).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(myLetter, MyLetterDTO.class);
    }

    @Override
    public String createMyLetter(MyLetterDTO newMyLetterDTO) {
        if(myLetterRepository.findById(newMyLetterDTO.getMyLetterId()).isPresent()) {
            String message = String.format("다시 시도해주세요. : %s", newMyLetterDTO.getMyLetterTitle());
            System.out.println("이미 존재하는 MyLetterId: " + newMyLetterDTO.getMyLetterId() + "입니다");
            throw new IllegalArgumentException(message);
        }
        String message = String.format("존재하지 않는 사용자 ID: %s", newMyLetterDTO.getWriterId());
        User user = userRepository.findById(newMyLetterDTO.getWriterId()).orElseThrow(() -> new NoSuchElementException(message));

        BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
        String letterId = scpwd.encode(newMyLetterDTO.getMyLetterId());

        final MyLetter myLetter = modelMapper.map(newMyLetterDTO, MyLetter.class);
        myLetter.setUser(user);
        myLetter.setMyLetterId(letterId);
        myLetterRepository.save(myLetter);
        return "새로운 편지가 전송되었습니다.";
    }

    @Override
    public String deleteMyLetter(String myLetterId, String writerId) {
        final Optional<MyLetter> myLetter = myLetterRepository.findByMyLetterIdAndWriterId(myLetterId, writerId);
        if (myLetter.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 편지입니다.");
        }
        final MyLetter foundMyLetter = myLetter.get();
        myLetterRepository.delete(foundMyLetter);
        return "선택한 편지를 삭제했습니다.";
    }
}
