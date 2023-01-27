package com.example.wero.core.user.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private String userId; // 회원ID
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
