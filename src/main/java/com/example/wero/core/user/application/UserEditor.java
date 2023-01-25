package com.example.wero.core.user.application;

import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserEditor {

    /**
     * 새로운 User 등록
     * DB에 등록된 user 확인
     * 하나도 없을 경우 가입된 user id 반환
     * @param newUser - 새로 등록된 User Entity
     * @return String id - 가입된 user id
     */
    String createUser(UserDTO newUser);

    /**
     * User 정보 변경
     * DB와 id pw 대조
     * 있을경우에만 update 실행
     * @param updateUser - 새롭게 업데이트할 유저 정보를 담고있는 Entity
     * @return user - 수정된 user 엔티티 객체
     */

    String updateUser(String id, String password, UserDTO updateUser);
}
