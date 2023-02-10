package com.example.wero.core.senduser.application;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import com.example.wero.core.senduser.domain.SendUser;
import com.example.wero.core.senduser.domain.SendUserDTO;
import com.example.wero.core.senduser.infrastructure.SendUserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SendUserManager implements SendUserEditor, SendUserFinder {
    private final SendUserRepository sendUserRepository;
    private final ModelMapper modelMapper;
    private final MyLetterRepository myLetterRepository;

    public SendUserManager(SendUserRepository sendUserRepository, ModelMapper modelMapper, MyLetterRepository myLetterRepository) {
        this.sendUserRepository = sendUserRepository;
        this.modelMapper = modelMapper;
        this.myLetterRepository = myLetterRepository;
    }

    @Override
    public String createUserLetter(MyLetter myLetter) {
        SendUser sendUser = myLetter.myLetterToSendUser(myLetter);
        sendUserRepository.save(sendUser);
        SendUserDTO sendUserDTO = modelMapper.map(sendUser, SendUserDTO.class);
        return sendUserDTO.getUserId();
    }

    @Override
    public List<SendUserDTO> findAllMySendLetters(String userId) {
        List<SendUser> foundUser = sendUserRepository.findAll(); // 일단 repository 에서 다 가져와
        // SendUser -> SendUserDTO 로 변환
        List<SendUserDTO> result = foundUser.stream().map(p -> modelMapper.map(p, SendUserDTO.class)).collect(Collectors.toList());
        System.out.println("---------result = " + result);
        // 모든 보낸 편지를 사용자 ID(userId)로 필터링
        List<SendUserDTO> filteredResult;
        filteredResult = result.stream()
                .filter(a -> a.getUserId().equals(userId))
                .collect(Collectors.toList());
        return filteredResult;
    }

    @Override
    public MyLetterDTO findSendLetter(String myLetterId) {
        String message = String.format("%s에 해당하는 MyLetter 가 없습니다.", myLetterId);
        final MyLetter myLetter = myLetterRepository.findById(myLetterId).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(myLetter, MyLetterDTO.class);
    }
 
    @Override
    public String deleteUserLetter(String letterIds) {
        String[] letters = letterIds.substring(2, letterIds.length() - 2).split("\",\"");
        for (String letterId : letters) {
            if (sendUserRepository.findByMyLetterId(letterId).isEmpty()) {
                String message = String.format("존재하지 않는 LetterID : %s", letterId);
                throw new IllegalArgumentException(message);
            }
            sendUserRepository.deleteByMyLetterId(letterId);
        }
        return "{\"message\" : \"" + "---- 삭제 성공 !!" + "\"}";

    }
}
