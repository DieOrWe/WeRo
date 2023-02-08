package com.example.wero.core.receiveduser.domain;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.user.domain.User;

import lombok.*;
import javax.persistence.*;

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
    

    public ReceivedUserDTO receivedUserDTO(ReceivedUser receivedUser){
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
