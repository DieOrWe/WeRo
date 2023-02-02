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

    private String myletterId;
    @NotBlank
    private String myLetterTitle;

    private String letterSendWhen;


    public SendUser sendUser (SendUserDTO sendUserDTO){
        return SendUser.builder()
                .userId(userId)
                .myletterId(myletterId)
                .myLetterTitle(myLetterTitle)
                .letterSendWhen(letterSendWhen)
                .build();
    }
}
