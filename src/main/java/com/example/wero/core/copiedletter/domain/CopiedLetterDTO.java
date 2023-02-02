package com.example.wero.core.copiedletter.domain;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CopiedLetterDTO {

    @NotBlank
    private String copiedLetterId;

    private String myLetterId;

    private String writerId;

    private String receiveUserId;

    private String copiedLetterTitle;

    private String copiedLetterContent;

    private String copiedLetterCreatedWhen;

    private boolean copiedLetterIsRead;

    private String copiedLetterIsReceivedWhen;

    private boolean copiedLetterIsSent;

    public CopiedLetter copiedLetter(CopiedLetterDTO toCopiedLetterDTO){
        return CopiedLetter.builder()
                .
    }


}
