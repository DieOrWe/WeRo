package com.example.wero.core.user.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * UserDTO 클라이언트의 요청에 사용할 DTO
 */

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private String userId; // 회원 ID
    @NotBlank
    private String userPw; // 비밀번호

    private String userCreatedWhen; // 생성일자
    @NotBlank
    private String userNickName; // 닉네임

    private String userEmail; // 이메일
    @NotNull
    private boolean userNotify; // 알림동의여부


    /**
     * UserDTO method
     * UserDTO to User
     * @return User user
     */
    public User toUser(UserDTO userDTO) {

        return User.builder()
                .userId(userId)
                .userPw(userPw)
                .userCreatedWhen(userCreatedWhen)
                .userNickName(userNickName)
                .userEmail(userEmail)
                .userNotify(userNotify)
                .build();
    }

}
