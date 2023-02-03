package com.example.wero.core.senduser.application;

import com.example.wero.core.myletter.application.MyLetterManager;
import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.senduser.domain.SendUser;
import com.example.wero.core.senduser.domain.SendUserDTO;
import com.example.wero.core.senduser.infrastructure.SendUserRepository;
import com.example.wero.core.user.domain.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SendUserManager implements SendUserEditor, SendUserFinder {
    private final SendUserRepository sendUserRepository;
    private final ModelMapper modelMapper;

    public SendUserManager(SendUserRepository sendUserRepository, ModelMapper modelMapper) {
        this.sendUserRepository = sendUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String createUserLetter(MyLetter myLetter) {
        SendUser sendUser = new SendUser();
        sendUser.setUserId(myLetter.getWriterId());
        sendUser.setMyLetterId(myLetter.getMyLetterId());
        sendUser.setMyLetterTitle(myLetter.getMyLetterTitle());
        sendUser.setLetterCreatedWhen(myLetter.getMyLetterCreatedWhen());
        sendUserRepository.save(sendUser);
        SendUserDTO sendUserDTO = modelMapper.map(sendUser, SendUserDTO.class);
        return sendUserDTO.getUserId();
    }
    
    @Override
    public List<SendUserDTO> findAllMySendLetters(String userId) {
        List<SendUser> foundUser = sendUserRepository.findAll();
        List<SendUserDTO> result;
        result = foundUser.stream().map(p -> modelMapper.map(p, SendUserDTO.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public MyLetterDTO findSendLetter(String myLetterId) {
        return null;
    }

    @Override
    public String deleteUserLetter(String letterId) {
        if(sendUserRepository.findByMyLetterId(letterId).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 편지 ID");
        }
        sendUserRepository.deleteByMyLetterId(letterId);
        return "---- 삭제 성공 !!";
    }
}
