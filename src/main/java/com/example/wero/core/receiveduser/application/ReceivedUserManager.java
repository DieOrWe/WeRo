package com.example.wero.core.receiveduser.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import com.example.wero.core.receiveduser.domain.ReceivedUser;
import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;
import com.example.wero.core.receiveduser.infrastructure.ReceivedUserRepository;
import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.infrastructure.UserRepository;
import com.example.wero.core.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
//@EnableJpaRepositories
public class ReceivedUserManager implements ReceivedUserFinder, ReceivedUserEditor{
    private final ModelMapper modelMapper;
    private final ReceivedUserRepository receivedUserRepository;
    private final MyLetterRepository myLetterRepository;

    private String secretKey;

    public ReceivedUserManager(ModelMapper modelMapper, ReceivedUserRepository receivedUserRepository, MyLetterRepository myLetterRepository) {
        this.modelMapper = modelMapper;
        this.receivedUserRepository = receivedUserRepository;
        this.myLetterRepository = myLetterRepository;
    }

    @Override
    public String createReceiveUser(){
        // 일단 myLetterRepository에서 myLetter 공개 동의 여부 필드가 true인 것들을 myLetters에 리스트로 받아오기
//        List<MyLetter> myLetters = myLetterRepository.findByMyLetterIsPrivate(true);
//        if (myLetters.isEmpty()) { // 공개동의한 편지가 아예 없는 경우
//            return "myLetter DB에 공개 가능한 편지가 없음.";
//        }
//
//        Collection<String> receivedUserLetterId = receivedUserRepository.ReceivedUserLetterId();
//        List<String> myLetterLetterId = myLetterRepository.MyLettersLetterId();
//        myLetterLetterId.removeAll(receivedUserLetterId);
//
//
//        ArrayList<MyLetter> myLetterEntity = new ArrayList<MyLetter>();
//        for (String letterId: myLetterLetterId) {
//            myLetterEntity.add(myLetterRepository.findAllByLetterId(letterId));
//        }
//
//        for (MyLetter letter: myLetterEntity) {
//            ReceivedUser receivedUser = myLetter.myLetterToReceivedUser(letter);
//            receivedUserRepository.save(receivedUser);
//        }

        // 1. ReceivedUser의 가장 최근 생성 시간이 ReceivedUser 의 마지막 index(Id)로 가져와서 String recentReceivedLetter 변수로 가져오기
        // 2. myLetterRepository에서 recentReceivedLetter 이후에 생성된 편지 쿼리문을 통해 List<MyLetter> newMyLetters 로 반환
        // 3. newMyLetters 를 ReceivedUser 로 만들기 - 만들 때 랜덤한 받은 회원 ID(USERID)가 생성


        return "receivedUser 생성 완료";
        }


    @Override
    public List<ReceivedUserDTO> findAllMyReceivedLetters(String RequestJwt){
        List<ReceivedUser> foundUser = receivedUserRepository.findAll(); // repository에서 가져오기
        // ReceivedUser -> ReceivedUserDTO로 변환
        List<ReceivedUserDTO> result = foundUser.stream().map(p-> modelMapper.map(p, ReceivedUserDTO.class)).collect(Collectors.toList());
        // 모든 받은 편지를 사용자 ID (userId)로 필터링
        String[] splitToken = RequestJwt.split("\\s+"); // Authoricate 할 때 "Bearer" 때문에 Jwt를 공백으로 나누어서 뒷부분만 받아줘야함.
        log.info(splitToken[1]);
        String userId = JwtUtil.getUserId(splitToken[1], secretKey);
        List<ReceivedUserDTO> filteredResult;
        filteredResult = result.stream().filter(a -> a.getUserId().equals(userId))
                .collect(Collectors.toList());
        return filteredResult;
    }

    @Override
    public MyLetterDTO findReceivedLetter(String myLetterId){
        String message = String.format("%s에 해당하는 MyLetter가 없습니다.", myLetterId);
        final MyLetter myLetter = myLetterRepository.findById(myLetterId).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(myLetter, MyLetterDTO.class);
    }
}