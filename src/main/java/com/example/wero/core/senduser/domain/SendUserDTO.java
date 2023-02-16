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
    
    @NotBlank
    private String userId;
    
    private String userNickName;
    
    private String myLetterId;
    
    @NotBlank
    private String myLetterTitle;
    
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
