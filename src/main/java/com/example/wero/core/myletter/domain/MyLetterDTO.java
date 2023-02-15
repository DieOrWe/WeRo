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
    
    @ApiModelProperty(value = "편지의 고유 ID (DB 저장 시 자동 생성)")
    @NotBlank
    private String myLetterId;
    
    @ApiModelProperty(value = "편지 작성자의 ID", example = "test1")
    private String writerId;
    
    @ApiModelProperty(value = "편지 제목", example = "test title")
    @NotBlank
    private String myLetterTitle;
    @ApiModelProperty(value = "편지 내용 (글자 수 255자 제한)", example = "test content")
    @NotBlank
    private String myLetterContent;
    
    @ApiModelProperty(value = "편지 생성 날짜 (DB 저장 시 자동 생성)", example = "2023-02-17 15:55:00")
    private String myLetterCreatedWhen;
    
    @ApiModelProperty(value = "편지 공개 동의 여부", example = "false")
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

