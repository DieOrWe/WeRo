package com.example.wero.core.receiveduser.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import com.example.wero.core.receiveduser.domain.ReceivedUser;
import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;
import com.example.wero.core.receiveduser.infrastructure.ReceivedUserRepository;
import com.example.wero.core.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReceivedUserManager implements ReceivedUserFinder, ReceivedUserEditor{
    private final ReceivedUserRepository receivedUserRepository;

    private final ModelMapper modelMapper;

    private final MyLetterRepository myLetterRepository;

    private String secretKey;

    public ReceivedUserManager(ReceivedUserRepository receivedUserRepository, ModelMapper modelMapper, MyLetterRepository myLetterRepository){
        this.receivedUserRepository = receivedUserRepository;
        this.modelMapper = modelMapper;
        this.myLetterRepository = myLetterRepository;
    }

    @Override
    public String createUserLetter(MyLetter myLetter){
        ReceivedUser receivedUser = myLetter.myLetterToReceivedUser(myLetter);
        receivedUserRepository.save(receivedUser);
        ReceivedUserDTO receivedUserDTO = modelMapper.map(receivedUser, ReceivedUserDTO.class);
        return receivedUserDTO.getUserId();
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