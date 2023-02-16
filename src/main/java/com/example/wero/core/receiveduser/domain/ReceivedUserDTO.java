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
    
    @NotBlank
    private String userId;
    
    private String myLetterId;
    
    private String writerNickName;
    
    @NotBlank
    private String myLetterTitle;
    
    private String letterReceivedWhen;
    
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
