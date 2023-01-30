package com.example.wero.core.myletter.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyLetterDTO {

    @NotBlank
    private String myletterId;

    private String userId;

    @NotBlank
    private String myletterTitle;
    @NotBlank
    private String myletterContent;

    private String myletterCreatedWhen;

    @NotNull
    private boolean myletterIsPrivate;


    public MyLetter myLetter(MyLetterDTO toMyLetterDTO) {
        return MyLetter.builder()
                .myletterId(myletterId)
                .userId(userId)
                .myletterTitle(myletterTitle)
                .myletterContent(myletterContent)
                .myletterCreatedWhen(myletterCreatedWhen)
                .myletterIsPrivate(myletterIsPrivate)
                .build();
    }
}