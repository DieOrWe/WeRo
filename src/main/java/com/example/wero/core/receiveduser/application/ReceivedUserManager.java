package com.example.wero.core.receiveduser.application;

import com.example.wero.core.jwtutils.JwtUtil;
import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import com.example.wero.core.receiveduser.domain.ReceivedUser;
import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;
import com.example.wero.core.receiveduser.infrastructure.ReceivedUserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
//@EnableJpaRepositories
public class ReceivedUserManager implements ReceivedUserFinder, ReceivedUserEditor {
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
            }
        } else {
            // 2. myLetterRepository 에서 recentReceivedLetter 이후에 생성된 편지 쿼리문을 통해 List<MyLetter> newMyLetters 로 반환
            if (myLetterRepository.myLetterFindAllByMyLetterIsPrivate().isEmpty()) {
                return "공개가능한 편지가 없습니다.";
            }
            String recentReceivedLetter = receivedUserRepository.RecentReceivedLetter();
            System.out.println("---------- recentReceivedLetter = " + recentReceivedLetter);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try { // simpleDateFormat.parse() 를 사용할 땐 무조건 try 구문에 넣어서 사용해야 compile 시 에러가 안남.
                Date time = simpleDateFormat.parse(recentReceivedLetter);
                List<MyLetter> newMyLetters = myLetterRepository.newMyLetters(time);
                System.out.println("---------- newMyLetters = " + newMyLetters);

                for (MyLetter newLetter : newMyLetters) {
                    System.out.println(newLetter);
                    ReceivedUser receivedUser = newLetter.myLetterToReceivedUser(newLetter);
                    receivedUserRepository.save(receivedUser);
                }
                return String.format("생성한 ReceivedUser 갯수: %d", newMyLetters.size());

            } catch (ParseException exception) {
                System.out.println("-------- exception StackTrace --------");
                exception.printStackTrace();
            }
        }

        if (receivedUserRepository.findByUserIdIsNull().isEmpty()) {
            return "새롭게 생성할 ReceivedUser 가 없습니다.";
        }
        List<ReceivedUser> newReceivedUsers = receivedUserRepository.findByUserIdIsNull();
        int numberOfNeededUser = newReceivedUsers.size();
        List<String> newUsers = myLetterRepository.randomSelectUserId(numberOfNeededUser);

        for (int i = 0; i < numberOfNeededUser; i++) {
            newReceivedUsers.get(i).setUserId(newUsers.get(i));
            receivedUserRepository.save(newReceivedUsers.get(i));
        }
        return "receivedUser 생성 완료";
    }


    @Override
    public List<ReceivedUserDTO> findAllMyReceivedLetters(String RequestJwt) {
        List<ReceivedUser> foundUser = receivedUserRepository.findAll(); // repository 에서 가져오기
        // ReceivedUser -> ReceivedUserDTO 로 변환
        List<ReceivedUserDTO> result = foundUser.stream().map(p -> modelMapper.map(p, ReceivedUserDTO.class)).collect(Collectors.toList());
        // 모든 받은 편지를 사용자 ID (userId)로 필터링
        String[] splitToken = RequestJwt.split("\\s+"); // Authenticate 할 때 "Bearer" 때문에 Jwt 를 공백으로 나누어서 뒷부분만 받아줘야함.
        log.info(splitToken[1]);
        String userId = JwtUtil.getUserId(splitToken[1], secretKey);
        List<ReceivedUserDTO> filteredResult;
        filteredResult = result.stream().filter(a -> a.getUserId().equals(userId))
                .collect(Collectors.toList());
        return filteredResult;
    }

    @Override
    public MyLetterDTO findReceivedLetter(String myLetterId) {
        String message = String.format("%s에 해당하는 MyLetter 가 없습니다.", myLetterId);
        final MyLetter myLetter = myLetterRepository.findById(myLetterId).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(myLetter, MyLetterDTO.class);
    }
}