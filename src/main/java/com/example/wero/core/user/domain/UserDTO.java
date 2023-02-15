package com.example.wero.core.user.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * UserDTO 클라이언트의 요청에 사용할 DTO
 */

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @ApiModelProperty(value = "회원ID", example = "test1")
    @NotBlank
    private String userId; // 회원 ID
    
    @ApiModelProperty(value = "비밀번호, DB 저장 시 decoding이 불가능하게 해쉬화 됨.")
    @NotBlank
    private String userPw; // 비밀번호

    @ApiModelProperty(value = "회원의 계정 생성일자")
    private String userCreatedWhen; // 생성일자
    
    @ApiModelProperty(value = "회원의 닉네임", example = "testNickName")
    @NotBlank
    private String userNickName; // 닉네임
    
    @ApiModelProperty(value = "회원의 이메일", example = "test1@gmail.com")
    private String userEmail; // 이메일
    @NotNull
    private boolean userNotify; // 알림동의여부


    /**
     * UserDTO method
     * UserDTO to User
     *
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
