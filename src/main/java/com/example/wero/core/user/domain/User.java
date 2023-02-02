package com.example.wero.core.user.domain;

import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "Users")
public class User {
    @Column(name = "userID")
    @Id
    private String userId; // 회원 ID
    @Column(name = "userPW")
    private String userPw; // 비밀번호
    @Column(name = "userCreateWhen")
    private String userCreatedWhen; // 생성일자
    @Column(name = "userNickName")
    private String userNickName; // 닉네임
    @Column(name = "userEmail")
    private String userEmail; // 이메일
    @Column(name = "userNotify")
    private boolean userNotify; // 알림동의여부
    
//    @Column(name = "num_of_send")
//    private

    public UserDTO toUserDTO(User user) {

        return UserDTO.builder()
                .userId(userId)
                .userPw(userPw)
                .userCreatedWhen(userCreatedWhen)
                .userNickName(userNickName)
                .userEmail(userEmail)
                .userNotify(userNotify)
                .build();
    }
}
