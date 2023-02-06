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


    public UserManager(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> usersEntity = userRepository.findAll();
        return usersEntity.stream().map(p -> modelMapper.map(p, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO findUser(String id) {
        String message = String.format("%s에 해당하는 User 가 없습니다.", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public String loginUser(UserDTO loginUser) {
        Long expiredMs = 3000 * 60 * 60L;


//        User foundUser = userRepository.findById(loginUser.getUserId()).orElseThrow(() ->
//                new NoSuchElementException("존재하지 않는 사용자입니다."));

        Optional<User> user = userRepository.findById(loginUser.getUserId());
        if (user.isPresent()) {
            User foundUser = user.get();

            BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
            boolean loginSuccessOrFail = scpwd.matches(loginUser.getUserPw(), foundUser.getUserPw());
            System.out.println(loginSuccessOrFail);

            if (!loginSuccessOrFail) {
                return "{\"message\" : \"" + "id 혹은 pw가 틀렸습니다." + "\"}";
            }
            return "{\"token\" : \"" + JwtUtil.createJwt(loginUser, secretKey, expiredMs) + "\"}";
        }
        return "존재하지 않는 사용자입니다.";

    }


    @Override
    public String createUser(UserDTO newUser) {

        boolean createSuccessOrFail = userRepository.findById(newUser.getUserId()).isPresent();

        if (createSuccessOrFail) {
            return "{\"message\" : \"" + "이미 존재하는 ID 입니다." + "\"}";
        }

        BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
        String password = scpwd.encode(newUser.getUserPw());
        User user = modelMapper.map(newUser, User.class);
        user.setUserPw(password);
        userRepository.save(user);

        return "{\"message\" : \"" + newUser.getUserId() + "\"}";
    }

    @Override
    public String updateUser(UserDTO updateUser) {

        User updatedUser = modelMapper.map(updateUser, User.class);
        userRepository.save(updatedUser);

        return "{\"message\" : \"" + "회원정보가 수정되었습니다." + "\"}";
    }


    @Override
    public String deleteUser(String id, String pw) {
        final Optional<User> user = userRepository.findByUserIdAndUserPw(id, pw);
        if (user.isPresent()) {
            final User foundUser = user.get();
            userRepository.delete(foundUser);
            return "{\"message\" : \"" + "회원탈퇴가 성공적으로 이루어졌습니다." + "\"}";
        }
        return "{\"message\" : \"" + "회원정보가 일치하지 않습니다." + "\"}";
    }

}
