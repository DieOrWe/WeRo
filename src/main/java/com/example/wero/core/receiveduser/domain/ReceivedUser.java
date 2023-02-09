package com.example.wero.core.receiveduser.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "ReceivedUsers")
public class ReceivedUser {
 
    @Id
    @Column(name = "index")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int index;
    @Column(name = "userId")
    private String userId;

    @Column(name = "myLetter_id")
    private String myLetterId;

    @Column(name = "user_nickname")
    private String writerNickName;

    @Column(name = "myLetter_title")
    private String myLetterTitle;

    private String letterReceivedWhen;

    private boolean isRead;


    public ReceivedUserDTO receivedUserDTO(ReceivedUser receivedUser) {
        return ReceivedUserDTO.builder()
                .userId(userId)
                .myLetterId(myLetterId)
                .writerNickName(writerNickName)
                .myLetterTitle(myLetterTitle)
                .letterReceivedWhen(letterReceivedWhen)
                .isRead(isRead)
                .build();
    }


}
