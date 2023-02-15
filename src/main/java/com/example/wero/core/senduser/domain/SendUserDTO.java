package com.example.wero.core.senduser.domain;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendUserDTO {
    
    @ApiModelProperty(value = "보낸 사람의 회원 ID", example = "test1")
    @NotBlank
    private String userId;

    @ApiModelProperty(value = "보낸사람의 회원 닉네임", example = "testNickName")
    private String userNickName;

    @ApiModelProperty(value = "편지의 고유 ID")
    private String myLetterId;
    
    @ApiModelProperty(value = "편지의 제목", example = "test title")
    @NotBlank
    private String myLetterTitle;

    @ApiModelProperty(value = "편지가 작성된 시간", example = "2023-02-17 15:55:00")
    private String letterCreatedWhen;


    public SendUser toSendUser(SendUserDTO sendUserDTO) {
        return SendUser.builder()
                .userId(userId)
                .userNickName(userNickName)
                .myLetterId(myLetterId)
                .myLetterTitle(myLetterTitle)
                .letterCreatedWhen(letterCreatedWhen)
                .build();
    }
}
