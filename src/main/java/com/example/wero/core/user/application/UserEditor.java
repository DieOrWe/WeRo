package com.example.wero.core.user.application;

import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;

public interface UserEditor {

    /**
     * 새로운 User 등록
     * DB에 등록된 user 확인
     * 하나도 없을 경우 가입된 user id 반환
     *
     * @param newUser - 새로 등록된 User Entity
     * @return String id - 가입된 user id
     */
    String createUser(UserDTO newUser);

    /**
     * user 의 비밀번호를 제외한 정보들을 변경할 수 있음
     * userId 는 변경 불가!!!
     * 변경하기 전에 user 가 원래 등록한 비밀번호도 입력해줘야 함.
     *
     * @param updateUser - 새롭게 업데이트할 유저 정보를 담고있는 Entity
     * @return user - 수정된 user 엔티티 객체
     */

    String updateUser(UserDTO updateUser);

    /**
     * user 의 비밀번호만 변경하는 메소드
     * 변경 전에 저장되어 있는 비밀번호를 client request 로 받고
     * client response 를 통해 변경 성공 여부를 알려줌
     *
     * @return String message
     */
    String updateUserPw(String userId, String changePw);

    /**
     * userId 를 통해 DB에 저장되어 있는 Pw와 일치하는지 확인
     * 비밀번호 확인이 되면 계정 탈퇴 성공 or 계정 탈퇴 실패
     *
     * @return String message
     */
    String deleteUser(String userId, String userPw);
    
}
