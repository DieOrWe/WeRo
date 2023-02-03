package com.example.wero.core.receiveduser.application;

import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.receiveduser.domain.ReceivedUser;
import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;
import com.example.wero.core.receiveduser.infrastructure.ReceivedUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceivedUserManager implements ReceivedUserFinder, ReceivedUserEditor{
    private final ReceivedUserRepository receivedUserRepository;

    private final ModelMapper modelMapper;

    public ReceivedUserManager(ReceivedUserRepository receivedUserRepository, ModelMapper modelMapper){
        this.receivedUserRepository = receivedUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReceivedUserDTO> findAllMyReceivedLetters(String userId){
        List<ReceivedUser> foundUser = receivedUserRepository.findAll();
        List<ReceivedUserDTO> result;
        result = foundUser.stream().map(p -> modelMapper.map(p,ReceivedUserDTO.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public MyLetterDTO findSendLetter(String myLetterId) {
        return null;
    }
}
