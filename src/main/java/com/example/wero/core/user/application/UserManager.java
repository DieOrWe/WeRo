package com.example.wero.core.user.application;

import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;
import com.example.wero.core.user.infrastructure.UserRepository;
import com.example.wero.core.utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserFinder, UserEditor {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Value("${jwt.secret}")
    private String secretKey;


    public UserManager(UserRepository userRepository, ModelMapper modelMapper ){
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> usersEntity = userRepository.findAll();
        // ModelMapper 사용 부분 - Entity -> DTO
        return usersEntity.stream().map(p -> modelMapper.map(p, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findUser(String id) {
        String message = String.format("%s에 해당하는 User 가 없습니다.", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public String loginUser(UserDTO newUser) {

        User foundUser = userRepository.findById(newUser.getUserId()).orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다."));

        String message = "id 혹은 pw가 틀렸습니다.";
        BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
        boolean loginSuccessOrFail = scpwd.matches(newUser.getUserPw(), foundUser.getUserPw());
        System.out.println(loginSuccessOrFail);
        if (!loginSuccessOrFail) {
            throw new NoSuchElementException(message);
        }
        Long expiredMs = 1000 * 60 * 60L;
        String json = "{\"token\" : \"" + JwtUtil.createJwt(newUser.getUserId(), secretKey, expiredMs) + "\"}";
        return json;
    }


    @Override
    public String createUser(UserDTO newUser) {
        if(userRepository.findById(newUser.getUserId()).isPresent()) {
            String json = "{\"message\" : \"" + "이미 존재하는 ID 입니다." + "\"}";
            return json;
        }

        BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
        String password = scpwd.encode(newUser.getUserPw());
        System.out.println("password: " + password);
        newUser.setUserPw(password);

        User user = modelMapper.map(newUser, User.class);
//        User user = userDTO.toUser(newUser);
        userRepository.save(user);
        return "{\"token\" : \"" + newUser.getUserId() + "\"}";
    }

    @Override
    public String updateUser(UserDTO updateUser) {
        final User foundUser = modelMapper.map(findUser(updateUser.getUserId()), User.class);
        User updatedUser = modelMapper.map(updateUser, User.class);
        userRepository.save(updatedUser);
        return "회원정보가 수정되었습니다.";
    }


    @Override
    public String deleteUser(String id, String pw) {
        final Optional<User> user = userRepository.findByUserIdAndUserPw(id, pw);
        if (user.isPresent()) { // id에 해당하는 User 가 존재할 경우
            final User foundUser = user.get();
            userRepository.delete(foundUser);
            return "회원탈퇴가 성공적으로 이루어졌습니다.";
        }
        return "회원정보가 일치하지 않습니다.";
    }

}
