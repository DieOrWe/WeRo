package com.example.wero.core.myletter.domain;

import lombok.*;


@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyLetterDTO {


    private String myletterId;

    private String userId;

    private String myletterTitle;

    private String myletterContent;

    private String myletterCreatedWhen;

    private boolean myletterIsPrivate;
    
}
