package com.example.wero.core.senduser.domain;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.user.domain.User;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@ToString
@Entity
@Table(name = "SendUsers")
public class SendUser {

    @Id
    @Column(name = "index")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int index;


    @Column(name = "userId")
    private String userId;

    @Column(name = "user_nickName")
    private String userNickName;

    @Column(name = "myLetter_id")
    private String myLetterId;


    @Column(name = "myletter_title")
    private String myLetterTitle;

    private String letterCreatedWhen;


    public SendUserDTO toSendUserDTO(SendUser sendUser){
        return SendUserDTO.builder()
                .userId(userId)
                .myLetterId(myLetterId)
                .userNickName(userNickName)
                .myLetterTitle(myLetterTitle)
                .letterCreatedWhen(letterCreatedWhen)
                .build();
    }

}
