package com.example.wero.core.senduser.application;

import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.senduser.domain.SendUser;
import com.example.wero.core.senduser.domain.SendUserDTO;
import com.example.wero.core.senduser.infrastructure.SendUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SendUserManager implements SendUserEditor {
    private final SendUserRepository sendUserRepository;
    private final ModelMapper modelMapper;
    
    public SendUserManager(SendUserRepository sendUserRepository, ModelMapper modelMapper) {
        this.sendUserRepository = sendUserRepository;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public SendUserDTO createUserLetter(MyLetterDTO myLetterDTO) {
        SendUser sendUser = new SendUser();
        sendUser.setUserId(myLetterDTO.getWriterId());
        sendUser.setMyLetterTitle(myLetterDTO.getMyLetterTitle());
        sendUser.setLetterCreatedWhen(myLetterDTO.getMyLetterCreatedWhen());
        sendUserRepository.save(sendUser);
        SendUserDTO sendUserDTO = modelMapper.map(sendUser, SendUserDTO.class);
        return sendUserDTO;
    }
}
