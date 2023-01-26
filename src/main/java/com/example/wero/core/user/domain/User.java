package com.example.wero.core.user.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@ToString
@Entity
@Table(name = "Users")
public class User {
    @Column(name = "userID")
    @Id
    private String userId; // 회원ID
    @Column(name = "userPW")
    private String userPw; // 비밀번호
    @Column(name = "userMobileNumber")
    private String userMobileNumber; // 휴대폰번호
    // Todo: 준 - 요즘은 Date말고 다른거 있지않나?
    @Column(name = "userCreateWhen")
    private String userCreatedWhen; // 생성일자
    @Column(name = "userNickName")
    private String userNickName; // 닉네임
    @Column(name = "userEmail")
    private String userEmail; // 이메일
    @Column(name = "userNotify")
    private boolean userNotify; // 알림동의여부

    public UserDTO toUserDTO(User user) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        return UserDTO.builder()
                .userId(userId)
                .userPw(userPw)
                .userMobileNumber(userMobileNumber)
                .userCreatedWhen(formatter.format(date))
                .userNickName(userNickName)
                .userEmail(userEmail)
                .userNotify(userNotify)
                .build();
    }

}
