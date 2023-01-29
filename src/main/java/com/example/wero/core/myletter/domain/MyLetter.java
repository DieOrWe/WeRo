package com.example.wero.core.myletter.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor

@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name = "MyLetters")
public class MyLetter {

    @Id
    @Column(name = "myLetterId")
    private String myLetterId;

//    @ManyToOne
//    @JoinColumn(name ="userID")
    private String writerId;

    private String myLetterTitle;

    private String myLetterContent;

    private String myLetterCreatedWhen;

    private boolean myLetterIsPrivate;

    public MyLetterDTO toMyLetterDTO(MyLetter myLetter) {
        return MyLetterDTO.builder()
                .myLetterId(myLetterId)
                .writerId(writerId)
                .myLetterTitle(myLetterTitle)
                .myLetterContent(myLetterContent)
                .myLetterCreatedWhen(myLetterCreatedWhen)
                .myLetterIsPrivate(myLetterIsPrivate)
                .build();
    }

}
