package com.example.wero.core.myletter.domain;

import com.example.wero.core.receiveduser.domain.ReceivedUser;
import com.example.wero.core.senduser.domain.SendUser;
import com.example.wero.core.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @Column(name = "myLetterId", updatable = false)
    private String myLetterId; // 편지 Id는 프론트에서 정해진 규칙에 맞춰서 작성됨(클라이언트가 작성하는 것이 아닌 front에서 작성

    @Column(name = "user_id", insertable = false, updatable = false)
    private String writerId;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;
    private String myLetterTitle;
    private String myLetterContent;
    @Column(name = "created_when")
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

    // 정적 팩토리 메소드(static factory method)
    public SendUser myLetterToSendUser(MyLetter myLetter) {
        System.out.println("************* myLetterID: ==" + myLetter.getMyLetterId());
        return SendUser.builder()
                .userId(myLetter.writerId)
                .userNickName(myLetter.user.getUserNickName())
                .myLetterId(myLetter.getMyLetterId())
                .myLetterTitle(myLetter.getMyLetterTitle())
                .letterCreatedWhen(myLetter.getMyLetterCreatedWhen())
                .build();
    }

    public ReceivedUser myLetterToReceivedUser(MyLetter myLetter){
        System.out.println("----------- MyLetterID" + myLetter.getMyLetterId());
        System.out.println("----------- UserId" + myLetter.user.getUserId());
        return ReceivedUser.builder()
                .userId(null)  // userId에는 작성자가 아니라, 받는 회원의 Id가 들어가야 됨. 후에 해당 메소드 호출 한 뒤에 userId를 토큰을 통해 지정해줘야함.
                .writerNickName(myLetter.user.getUserNickName()) // 보낸 사람 닉네임
                .myLetterId(myLetter.getMyLetterId()) // 편지 아이디
                .myLetterTitle(myLetter.getMyLetterTitle()) // 편지 제목
                .letterReceivedWhen(myLetterCreatedWhen) // 만약 편지 작성 시간에 수신을 바로 한다고 가정했을때는 OK
                .isRead(false)
                .build();
    }

}
