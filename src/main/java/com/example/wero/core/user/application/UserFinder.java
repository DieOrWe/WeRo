package com.example.wero.core.user.application;

import com.example.wero.core.user.domain.UserDTO;

import java.util.List;
import java.util.NoSuchElementException;

public interface UserFinder {
    /**
     * DB에 등록된 모든 User 리스트 반환
     * 하나도 없을 경우 비어있는 리스트 반환
     *
     * @return List<User> users
     */
    List<UserDTO> findAll();

    /**
     * 전달된 id와 일치하는 하나의 User 반환
     * DB에 등록된 user 반환
     * 하나도 없을 경우 null 반환
     *
     * @return User user
     * @throws NoSuchElementException - User 의 id가 유효하지 않을때
     */

    UserDTO findUser(String userId);

    /**
     * id와 pw로 로그인기능
     * DB와 id, pw 조회
     *
     * @return String JWT 토큰
     */
    String loginUser(UserDTO loginUser);

    Boolean checkPw(String userId, String userPw);

    String findEmail(String userId);
    

}
