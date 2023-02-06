package com.example.wero.core.receiveduser.domain;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    public ReceivedUser ToReceivedUser(ReceivedUserDTO receivedUserDTO){
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
