package com.example.wero.core.user.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * UserDTO클라이언트의 요청에 사용할 DTO
 */

@Getter @Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    private String userId; // 회원ID
    @NotBlank
    @Length(min = 8, max = 12)
    @Pattern(regexp = "^[0-9]*$",message = "비밀번호는 숫자만 입력가능합니다.")
    private String userPw; // 비밀번호


    @Pattern(regexp = "^01(?:0|1|[6-9])-?(?:\\d{3}|\\d{4})-?\\d{4}$", message = "010******** or 010-****-****")
    private String userMobileNumber; // 휴대폰번호
    private String userCreatedWhen; // 생성일자
    @NotBlank
    private String userNickName; // 닉네임
    private String userEmail; // 이메일

    @NotNull
    private boolean userNotify; // 알림동의여부


    /**
     * UserDTO method
     * UserDTO to User
     * @param userDTO
     * @return User user
     */
    public User toUser(UserDTO userDTO) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        return User.builder()
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
