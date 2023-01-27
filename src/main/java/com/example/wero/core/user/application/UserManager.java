package com.example.wero.core.user.application;

import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;
import com.example.wero.core.user.infrastructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserManager implements UserFinder, UserEditor, UserLoginManager {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


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
        String message = String.format("%s에 해당하는 User가 없습니다.", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(message));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public boolean infoUser(String userId, String userPw) {
        return userRepository.findByUserIdAndUserPw(userId, userPw).isPresent();

//        String message = String.format("id 혹은 pw가 틀렸습니다.");
//        User foundUser = userRepository.findByUserIdAndUserPw(userId, userPw).orElseThrow(() -> new NoSuchElementException(message));
//            return modelMapper.map(foundUser, UserDTO.class);
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
        if(userRepository.findById(newUser.getUserId()).isPresent()) {
            String message = String.format("이미 존재하는 user id입니다. %s", newUser.getUserId());
            throw new IllegalArgumentException(message);
        }
        User user = modelMapper.map(newUser, User.class);
//        User user = userDTO.toUser(newUser);
        userRepository.save(user);


        return newUser.getUserId();
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
        if (user.isPresent()) { // id에 해당하는 User가 존재할 경우
            final User foundUser = user.get();
            userRepository.delete(foundUser);
            return "회원탈퇴가 성공적으로 이루어졌습니다.";
        }
        return "회원정보가 일치하지 않습니다.";
    }

}
