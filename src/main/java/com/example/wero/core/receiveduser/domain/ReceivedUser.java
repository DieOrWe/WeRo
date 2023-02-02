package com.example.wero.core.receiveduser.domain;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.user.domain.User;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "ReceivedUsers")
public class ReceivedUser {

    @Id
    @Column(name = "userId")
    private String userId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="myLetter_id")
    private MyLetter myLetter;
    @Column(name = "myLetter_id", insertable = false, updatable = false)
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
