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
    @Column(name = "myletterId")
    private String myletterId;

//    @ManyToOne
//    @JoinColumn(name ="userID")
    private String userId;

    private String myletterTitle;

    private String myletterContent;

    private String myletterCreatedWhen;

    private boolean myletterIsPrivate;

    public MyLetterDTO toMyLetterDTO(MyLetter myLetter) {
        return MyLetterDTO.builder()
                .myletterId(myletterId)
                .userId(userId)
                .myletterTitle(myletterTitle)
                .myletterContent(myletterContent)
                .myletterCreatedWhen(myletterCreatedWhen)
                .myletterIsPrivate(myletterIsPrivate)
                .build();
    }

}
