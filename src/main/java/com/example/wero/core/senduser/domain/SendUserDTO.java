package com.example.wero.core.senduser.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendUserDTO {
    @NotBlank
    private String userId;

    private String myLetterId;
    @NotBlank
    private String myLetterTitle;

    private String letterCreatedWhen;


    public SendUser toSendUser (SendUserDTO sendUserDTO){
        return SendUser.builder()
                .userId(userId)
                .myLetterId(myLetterId)
                .myLetterTitle(myLetterTitle)
                .letterCreatedWhen(letterCreatedWhen)
                .build();
    }
}
