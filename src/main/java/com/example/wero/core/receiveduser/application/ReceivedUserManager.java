package com.example.wero.core.receiveduser.application;

import com.example.wero.core.receiveduser.infrastructure.ReceivedUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReceivedUserManager implements ReceivedUserFinder, ReceivedUserEditor{
    private final ReceivedUserRepository receivedUserRepository;

    private final ModelMapper modelMapper;

    public ReceivedUserManager(ReceivedUserRepository receivedUserRepository, ModelMapper modelMapper){
        this.receivedUserRepository = receivedUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String
}
