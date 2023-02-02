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


}
