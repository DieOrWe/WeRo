package com.example.wero.core.user.application;

import com.example.wero.core.jwtutils.JwtUtil;
import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;
import com.example.wero.core.user.domain.UserVo;
import com.example.wero.core.user.infrastructure.UserRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Value("${key.spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;


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
        boolean createSuccessOrFail2 = userRepository.findByUserEmail(newUser.getUserEmail()).isPresent();

        if (createSuccessOrFail) {
            return "{\"message\" : \"" + "이미 존재하는 ID 입니다." + "\"}";
        } else if (createSuccessOrFail2) {
            return "{\"message\" : \"" + "이미 존재하는 Email 입니다." + "\"}";
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
            updateUserEntity.setUserPw(scpwd.encode(updateUser.getUserPw()));
            userRepository.save(updateUserEntity);
            Long expiredMs = 3000 * 60 * 60L;
            return "{\"token\" : \"" + JwtUtil.createJwt(updateUser, secretKey, expiredMs) + "\"}";
        }
        System.out.println("-------- 등록된 ID와 다른 값이 전달됨.");
        return "{\"message\" : \"" + "pw가 틀렸습니다." + "\"}";
    }

    @Override
    public String updateUserPw(String userId, String changePw) {
        Optional<User> foundUser = userRepository.findById(userId);
        if (foundUser.isEmpty()) {
            return "{\"message\" : \"" + "비밀번호 변경 실패(Not Found User)." + "\"}";
        }
        BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
        String password = scpwd.encode(changePw);
        User pwUpdateUser = foundUser.get();
        pwUpdateUser.setUserPw(password);
        userRepository.save(pwUpdateUser);
        return "{\"message\" : \"" + "비밀번호 변경이 성공적으로 이루어졌습니다." + "\"}";
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

    @Override
    public String findId(String userEmail) {
        // ToDo: 이메일 하나만 만들 수 있게 해야됨 - 여러 이메일로 여러 아이디 만들면 답이 없음
        Optional<User> foundUser = userRepository.findByUserEmail(userEmail);
        if (foundUser.isEmpty()) {
            return "{\"message\" : \"" + "등록된 계정 정보가 없습니다." + "\"}";
        }

        UserDTO foundUserDTO = modelMapper.map(foundUser.get(), UserDTO.class);
        System.out.println(foundUserDTO);
        return foundUserDTO.getUserId();
    }

    @Override
    public String findPw(UserVo userVo) {
        Optional<User> foundUser = userRepository.findById(userVo.getUserId());
        if (foundUser.isEmpty()) {
            return "{\"message\" : \"" + "등록된 계정 정보가 없습니다." + "\"}";
        }
        User user = foundUser.get();
        if (user.getUserEmail().equals(userVo.getUserEmail())) {
            BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
            String password = scpwd.encode(userVo.getUserPw());
            user.setUserPw(password);
            userRepository.save(user);
            return "{\"message\" : \"" + "비밀번호가 성공적으로 변경 되었습니다." + "\"}";
        }
        return "{\"message\" : \"" + "입력한 Email 주소가 등록된 사용자 정보와 일치하지 않습니다." + "\"}";
    }


    @Override
    public String getGoogleAuthUrl() {
        String reqUrl = "https://accounts.google.com"
                + "/o/oauth2/v2/auth?client_id="
                + googleClientId
                + "&redirect_uri="
                + "http://localhost:8080/callback"
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";
        return reqUrl;
    }
}
