package com.example.wero.core.receiveduser.domain;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class ReceivedUserDTO {
 
    @ApiModelProperty(value = "받은 사용자의 회원 ID", example = "test1")
    @NotBlank
    private String userId;
    
    @ApiModelProperty(value = "편지의 고유 ID")
    private String myLetterId;
    
    @ApiModelProperty(value = "편지 작성자의 닉네임", example = "testNickName")
    private String writerNickName;
    
    @ApiModelProperty(value = "편지 제목", example = "test title")
    @NotBlank
    private String myLetterTitle;
    
    @ApiModelProperty(value = "편지를 받은 시간(DB 저장 시 자동 생성)", example = "2023-02:17 15:57")
    private String letterReceivedWhen;
    
    @ApiModelProperty(value = "편지를 읽었는지 안읽었는지 여부", example = "true/false")
    @NotNull
    private boolean isRead;

    public ReceivedUser ToReceivedUser(ReceivedUserDTO receivedUserDTO) {
        return ReceivedUser.builder()
                .userId(userId)
                .myLetterId(myLetterId)
                .writerNickName(writerNickName)
                .myLetterTitle(myLetterTitle)
                .letterReceivedWhen(letterReceivedWhen)
                .isRead(isRead)
                .build();
    }

}
