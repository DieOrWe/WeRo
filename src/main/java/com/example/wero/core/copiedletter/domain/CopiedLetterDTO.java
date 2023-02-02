package com.example.wero.core.copiedletter.domain;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CopiedLetterDTO {

    @NotBlank
    private String copiedLetterId;
    @NotBlank
    private String myLetterId;

    private String writerId;
    @NotBlank
    private String receiveUserId;
    @NotBlank
    private String copiedLetterTitle;
    @NotBlank
    private String copiedLetterContent;

    private String copiedLetterCreatedWhen;

    @NotNull
    private boolean copiedLetterIsRead;

    private String copiedLetterIsReceivedWhen;
    @NotNull
    private boolean copiedLetterIsSent;

    public CopiedLetter copiedLetter(CopiedLetterDTO toCopiedLetterDTO){
        return CopiedLetter.builder()
                .copiedLetterId(copiedLetterId)
                .myLetterId(myLetterId)
                .writerId(writerId)
                .receiveUserId(receiveUserId)
                .copiedLetterTitle(copiedLetterTitle)
                .copiedLetterContent(copiedLetterContent)
                .copiedLetterIsRead(copiedLetterIsRead)
                .copiedLetterIsReceivedWhen(copiedLetterIsReceivedWhen)
                .copiedLetterIsSent(copiedLetterIsSent)
                .build();
    }


}
