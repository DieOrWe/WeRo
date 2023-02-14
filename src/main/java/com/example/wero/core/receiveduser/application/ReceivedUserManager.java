package com.example.wero.core.receiveduser.application;

import com.example.wero.core.jwtutils.JwtUtil;
import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import com.example.wero.core.receiveduser.domain.ReceivedUser;
import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;
import com.example.wero.core.receiveduser.infrastructure.ReceivedUserRepository;

import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;
import com.example.wero.core.user.infrastructure.UserRepository;
import com.example.wero.core.websocket.domain.BackMessage;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.HtmlUtils;

@Slf4j
@Service
@ToString
//@EnableJpaRepositories
public class ReceivedUserManager implements ReceivedUserFinder, ReceivedUserEditor {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ReceivedUserRepository receivedUserRepository;
    private final MyLetterRepository myLetterRepository;

    private String secretKey;
    
    public ReceivedUserManager(ModelMapper modelMapper, UserRepository userRepository, ReceivedUserRepository receivedUserRepository, MyLetterRepository myLetterRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.receivedUserRepository = receivedUserRepository;
        this.myLetterRepository = myLetterRepository;
    }
    
    @Override
    public String createReceiveUser() {
        // 1. ReceivedUser 의 가장 최근 생성 시간이 ReceivedUser 의 마지막 index(Id)로 가져와서 String recentReceivedLetter 변수로 가져오기
        // ReceivedUser 가 null 일 때, nullPoint 에러 발생

        // 1-1. ReceivedUser 에 데이터가 없는 경우 vs 데이터가 있는 경우 구분
        if (receivedUserRepository.findAll().isEmpty()) {
            if (myLetterRepository.myLetterFindAllByMyLetterIsPrivate().isEmpty()) {
                return "공개가능한 편지가 없습니다.";
            } else {
                List<MyLetter> myLetters = myLetterRepository.myLetterFindAllByMyLetterIsPrivate();
                for (MyLetter newLetter : myLetters) {
                    System.out.println(newLetter);
                    ReceivedUser receivedUser = newLetter.myLetterToReceivedUser(newLetter);
                    receivedUserRepository.save(receivedUser);
                }
                return createUserIdInReceivedUser();
            }
        } else {
            // 2. myLetterRepository 에서 recentReceivedLetter 이후에 생성된 편지 쿼리문을 통해 List<MyLetter> newMyLetters 로 반환
            if (myLetterRepository.myLetterFindAllByMyLetterIsPrivate().isEmpty()) {
                return "공개가능한 편지가 없습니다.";
            }
            String recentReceivedLetter  = receivedUserRepository.RecentReceivedLetter();
            System.out.println("---------- recentReceivedLetter = " + recentReceivedLetter);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try { // simpleDateFormat.parse() 를 사용할 땐 무조건 try 구문에 넣어서 사용해야 compile 시 에러가 안남.
                Date time = simpleDateFormat.parse(recentReceivedLetter);
                List<MyLetter> newMyLetters = myLetterRepository.newMyLetters(time);
                System.out.println("---------- newMyLetters = " + newMyLetters);

                for (MyLetter newLetter : newMyLetters) {
                    System.out.println(newLetter);
                    ReceivedUser receivedUser = newLetter.myLetterToReceivedUser(newLetter);
                    receivedUserRepository.save(receivedUser);
                }
                return createUserIdInReceivedUser();

            } catch (ParseException exception) {
                System.out.println("-------- exception StackTrace --------");
                exception.printStackTrace();
            }
        }
        return "createReceivedUser() called";
    }
    
    public String createUserIdInReceivedUser() {
        if (receivedUserRepository.findByUserIdIsNull().isEmpty()) {
            System.out.println("새롭게 생성할 ReceivedUser 가 없습니다.");
            return "새롭게 생성할 ReceivedUser 가 없습니다.";
        } else {
            List<ReceivedUser> newReceivedUsers = receivedUserRepository.findByUserIdIsNull();
    
    
            for (ReceivedUser receivedUser : newReceivedUsers) {
                Optional<String> tempUserID = myLetterRepository.getUserIdById(receivedUser.getWriterNickName());
                if (tempUserID.isEmpty()) {
                    return "myLetter를 작성한 유저가 없습니다.";
                }
                System.out.println("========= tempUserId : " + tempUserID);
                receivedUser.setUserId(tempUserID.get());
                String writerNickName = userRepository.findUserNickName(receivedUser.getWriterNickName());
                System.out.println("---- writerNickName : " + writerNickName);
                receivedUser.setWriterNickName(writerNickName);
                System.out.println("========= receivedUser : " + receivedUser);
                receivedUserRepository.save(receivedUser);
            }
    
            return "ReceivedUser 생성 완료!";
        }
    }
    
    public BackMessage returnBackMessage(List<ReceivedUser> newReceivedUsers) {
        List<ReceivedUserDTO> newReceivedUserDTOS = newReceivedUsers.stream().map(p -> modelMapper.map(p, ReceivedUserDTO.class)).collect(Collectors.toList());
        return new BackMessage(HtmlUtils.htmlEscape(newReceivedUserDTOS.toString()));
    }


    @Override
    public List<ReceivedUserDTO> findAllMyReceivedLetters(String userId) {
        List<ReceivedUser> foundUser = receivedUserRepository.findAll(); // repository 에서 가져오기
        List<ReceivedUserDTO> result = foundUser.stream().map(p -> modelMapper.map(p, ReceivedUserDTO.class)).collect(Collectors.toList());
        System.out.println("===== userId :" + result);
        List<ReceivedUserDTO> filteredResult;
        filteredResult = result.stream()
                .filter(a -> a.getUserId().equals(userId))
                .collect(Collectors.toList());
        return filteredResult;
    }

    @Override
    public MyLetterDTO findReceivedLetter(String myLetterId) {
        String message = String.format("%s에 해당하는 MyLetter 가 없습니다.", myLetterId);
        final MyLetter myLetter = myLetterRepository.findById(myLetterId).orElseThrow(() -> new NoSuchElementException(message));
        Optional<ReceivedUser> receivedUser = receivedUserRepository.findByMyLetterId(myLetterId);
        ReceivedUser readReceivedUser = receivedUser.get();
        readReceivedUser.setRead(true);
        receivedUserRepository.save(readReceivedUser);
        
        return modelMapper.map(myLetter, MyLetterDTO.class);
    }
    
    @Override
    public String deleteReceivedUser(String myLetterId) {
        String[] letters = myLetterId.substring(2, myLetterId.length() - 2).split("\",\"");
    
        for (String letterId : letters) {
            if (receivedUserRepository.deleteByMyLetterId(letterId).isEmpty()) {
                String message = String.format("존재하지 않는 LetterID : %s", letterId);
                throw new IllegalArgumentException(message);
            }
            receivedUserRepository.deleteByMyLetterId(letterId);
        }
        System.out.println("deleteByMyLetterId(myLetterId) : " + myLetterId + " -- receivedUser 삭제.");
        return "편지 삭제를 완료했습니다.";
    }
}