package com.example.wero.core.user.application;

import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;
import com.example.wero.core.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *  findAll = Read
 *  findUser =
 */
@Service
public class UserManager implements UserFinder, UserEditor, UserLoginManager {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserLoginManager userLoginManager;

    public UserManager(UserRepository userRepository, ModelMapper modelMapper, UserLoginManager userLoginManager) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userLoginManager = userLoginManager;
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> usersEntity = userRepository.findAll();
        // ModelMapper 사용 부분 - Entity -> DTO
        return usersEntity.stream().map(p -> modelMapper.map(p, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findUser(String id) {
        String message = String.format("%s에 해당하는 User가 없습니다.", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public String checkPw(String inputId, String inputPW) {
        String message = "비밀번호가 일치하지 않습니다.";
        UserDTO userDTO = findUser(inputId);

        if (inputPW.equals(userDTO.getUserPw())) {
            return userDTO.getUserNickName();
        } else return message;
    }

    @Override
    public String createUser(UserDTO newUser) {
//        if(userRepository.findById(newUser.getUserId()).isPresent()) {
//            String message = String.format("이미 존재하는 user id입니다. %s", newUser.getUserId());
//            throw new IllegalArgumentException(message);
//        }
        User user = modelMapper.map(newUser, User.class);
        userRepository.save(user);


        return newUser.getUserId();
    }

    @Override
    public String updateUser(@RequestParam("userID") String id, @RequestParam("userPW") String pw, @RequestBody UserDTO updateUser) {
//        if(userRepository.findById(newUser.getUserId()).isPresent()) {
//            String message = String.format("이미 존재하는 user id입니다. %s", newUser.getUserId());
//            throw new IllegalArgumentException(message);
//        }

        return updateUser.getUserId();
    }
}
