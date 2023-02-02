package com.example.wero.core.copiedletter.domain;

import com.example.wero.core.myletter.domain.MyLetter;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.user.domain.User;
import lombok.*;

import javax.persistence.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CopiedLetter")
public class CopiedLetter {

    @Id
    @Column(name = "copiedLetterId")
    private String copiedLetterId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;
    @Column(name = "user_id", insertable = false, updatable = false)
    private String writerId;

    private boolean myLetterIsRead;
    private String myLetterTitle;

    private String myLetterContent;

//    private List<String> myLetterEmotionTags; // ToDo: DB에 저장할 때는 json 타입으로 변경! list type convert to json type!!

    private String myLetterCreatedWhen;

    private boolean myLetterIsPrivate;

    public MyLetterDTO toMyLetterDTO(MyLetter myLetter) {
        return MyLetterDTO.builder()
//                .myLetterId(myLetterId)
                .myLetterIsRead(myLetterIsRead)
                .writerId(writerId)
                .myLetterTitle(myLetterTitle)
                .myLetterContent(myLetterContent)
//                .myLetterEmotionTags(myLetterEmotionTags)
                .myLetterCreatedWhen(myLetterCreatedWhen)
                .myLetterIsPrivate(myLetterIsPrivate)
                .build();
    }
}
