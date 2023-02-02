package com.example.wero.core.senduser.domain;

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
@Table(name = "SendUsers")
public class SendUser {

    @Id
    @Column(name = "userId")
    private String userId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="myletter_id")
    private MyLetter myletter;
    @Column(name = "myletter_id", insertable = false, updatable = false)
    private String myletterId;


    @Column(name = "myletter_title")
    private String myLetterTitle;

    private String letterSendWhen;


    public SendUserDTO sendUserDTO(SendUser sendUser){
        return SendUserDTO.builder()
                .userId(userId)
                .myletterId(myletterId)
                .myLetterTitle(myLetterTitle)
                .letterSendWhen(letterSendWhen)
                .build();
    }

}
