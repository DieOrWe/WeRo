package com.example.wero.core.myletter.domain;


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
public class MyLetterDTO {
    
    @NotBlank
    private String myLetterId;

    private String writerId;
    
    @NotBlank
    private String myLetterTitle;

    @NotBlank
    private String myLetterContent;

    private String myLetterCreatedWhen;
    
    @NotNull
    private boolean myLetterIsPrivate;



    public MyLetter myLetter(MyLetterDTO toMyLetterDTO) {
        return MyLetter.builder()
                .myLetterId(myLetterId)
                .writerId(writerId)
                .myLetterTitle(myLetterTitle)
                .myLetterContent(myLetterContent)
                .myLetterCreatedWhen(myLetterCreatedWhen)
                .myLetterIsPrivate(myLetterIsPrivate)
                .build();
    }
}

