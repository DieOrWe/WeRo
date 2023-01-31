package com.example.wero.core.myletter.domain;

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
    @Column(name = "myLetterId")
    private String myLetterId; // 편지 Id는 프론트에서 정해진 규칙에 맞춰서 작성됨(클라이언트가 작성하는 것이 아닌 front에서 작성

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY) // fetchType - 지연 로딩(편지 객체를 사용하는 시점에만 사용하기 때문에 즉시 로딩할 필요가 없음)
    @JoinColumn(name ="userID") // 외래키
    private String writerId;

    private String myLetterTitle;

    private String myLetterContent;

//    private List<String> myLetterEmotionTags; // ToDo: DB에 저장할 때는 json 타입으로 변경! list type convert to json type!!

    private String myLetterCreatedWhen;

    private boolean myLetterIsPrivate;

    public MyLetterDTO toMyLetterDTO(MyLetter myLetter) {
        return MyLetterDTO.builder()
                .myLetterId(myLetterId)
                .writerId(writerId)
                .myLetterTitle(myLetterTitle)
                .myLetterContent(myLetterContent)
//                .myLetterEmotionTags(myLetterEmotionTags)
                .myLetterCreatedWhen(myLetterCreatedWhen)
                .myLetterIsPrivate(myLetterIsPrivate)
                .build();
    }

}
