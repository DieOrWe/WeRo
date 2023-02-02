package com.example.wero.core.receiveduser.domain;

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
    @JoinColumn(name="user_nickname")
    private User user;
    @Column(name = "user_nickname", insertable = false, updatable = false)
    private String writerNickName;

    private String myLetterTitle;

    private String letterReceivedWhen;

    private boolean isRead;


}
