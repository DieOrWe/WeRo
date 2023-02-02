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

    private String myletterId;

    private String writerNickName;

    @NotBlank
    private String myLetterTitle;

    private String letterReceivedWhen;

    @NotNull
    private boolean isRead;

    public ReceivedUser receivedUser(ReceivedUserDTO receivedUserDTO){
        return ReceivedUser.builder()
                .userId(userId)
                .myletterId(myletterId)
                .writerNickName(writerNickName)
                .myLetterTitle(myLetterTitle)
                .letterReceivedWhen(letterReceivedWhen)
                .isRead(isRead)
                .build();
    }

}
