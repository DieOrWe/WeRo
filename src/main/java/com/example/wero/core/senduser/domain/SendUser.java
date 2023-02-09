package com.example.wero.core.senduser.domain;

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
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
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


    public SendUserDTO toSendUserDTO(SendUser sendUser) {
        return SendUserDTO.builder()
                .userId(userId)
                .myLetterId(myLetterId)
                .userNickName(userNickName)
                .myLetterTitle(myLetterTitle)
                .letterCreatedWhen(letterCreatedWhen)
                .build();
    }

}
