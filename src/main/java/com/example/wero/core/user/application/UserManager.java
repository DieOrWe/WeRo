package com.example.wero.core.user.application;

import com.example.wero.core.jwtutils.JwtUtil;
import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;
import com.example.wero.core.user.infrastructure.UserRepository;

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
    public Boolean checkPw(String userId, String userPw) {
        final Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
            return scpwd.matches(userPw, foundUser.getUserPw());
        }
        return false;
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
        String id = loginUser.getUserId();

        if (userRepository.findById(id).isEmpty()) {
            return "{\"message\" : \"" + "존재하지 않는 사용자 입니다." + "\"}";
        }
        Optional<User> foundUser = userRepository.findById(id);
        final User user = foundUser.get();
        BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
        boolean loginSuccessOrFail = scpwd.matches(loginUser.getUserPw(), user.getUserPw());
        if (!loginSuccessOrFail) {
            return "{\"message\" : \"" + "id 혹은 pw가 틀렸습니다." + "\"}";
        }
        return "{\"token\" : \"" + JwtUtil.createJwt(loginUser, secretKey, expiredMs) + "\"}";
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

        final Optional<User> user = userRepository.findById(updateUser.getUserId());
        if (user.isPresent()) {
            User foundUser = user.get();
            BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
            boolean loginSuccessOrFail = scpwd.matches(updateUser.getUserPw(), foundUser.getUserPw());

            if (!loginSuccessOrFail) {
                return "{\"message\" : \"" + "pw가 틀렸습니다." + "\"}";
            }
            final User updateUserEntity = modelMapper.map(updateUser, User.class);
            userRepository.save(updateUserEntity);
            Long expiredMs = 3000 * 60 * 60L;
            return "{\"token\" : \"" + JwtUtil.createJwt(updateUser, secretKey, expiredMs) + "\"}";
        }
        return "{\"message\" : \"" + "pw가 틀렸습니다." + "\"}";
    }

    @Override
    public String updateUserPw(String userId, String userPw, String changePw) {
        final Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
            boolean loginSuccessOrFail = scpwd.matches(userPw, foundUser.getUserPw());

            if (!loginSuccessOrFail) {
                return "{\"message\" : \"" + "pw가 틀렸습니다." + "\"}";
            }
            foundUser.setUserPw(changePw);
            userRepository.save(foundUser);
            return "{\"message\" : \"" + "비밀번호 변경이 성공적으로 이루어졌습니다." + "\"}";
        }
        return "{\"message\" : \"" + "pw가 틀렸습니다." + "\"}";
    }

    @Override
    public String deleteUser(String id, String pw) {
        final Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User foundUser = user.get();
            BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
            boolean loginSuccessOrFail = scpwd.matches(pw, foundUser.getUserPw());

            if (!loginSuccessOrFail) {
                return "{\"message\" : \"" + "pw가 틀렸습니다." + "\"}";
            }
            userRepository.deleteById(id);
            return "{\"message\" : \"" + "회원탈퇴가 성공적으로 이루어졌습니다." + "\"}";
        }
        return "{\"message\" : \"" + "회원정보가 일치하지 않습니다." + "\"}";
    }

}
