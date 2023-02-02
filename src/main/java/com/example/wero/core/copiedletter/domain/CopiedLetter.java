package com.example.wero.core.copiedletter.domain;

import com.example.wero.core.user.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
@Table(name="CopiedLetter")
public class CopiedLetter {
    @Id
    @Column(name = "copiedLetterId")
    private String copiedLetterId;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;
    @NotBlank
    private String myLetterId;
    @Column(name= "user_id", insertable = false, updatable = false)
    private String writerId;
    @NotBlank
    private String receiveUserId;
    @NotBlank
    private String copiedLetterTitle;
    @NotBlank
    private String copiedLetterContent;

    private String copiedLetterCreatedWhen;

    @NotNull
    private boolean copiedLetterIsRead;

    private String copiedLetterIsReceivedWhen;
    @NotNull
    private boolean copiedLetterIsSent;

    public CopiedLetterDTO toCopiedLetterDTO(CopiedLetter copiedLetter){
        return CopiedLetterDTO.builder()
                .copiedLetterId(copiedLetterId)
                .myLetterId(myLetterId)
                .writerId(writerId)
                .receiveUserId(receiveUserId)
                .copiedLetterTitle(copiedLetterTitle)
                .copiedLetterContent(copiedLetterContent)
                .copiedLetterIsRead(copiedLetterIsRead)
                .copiedLetterIsReceivedWhen(copiedLetterIsReceivedWhen)
                .copiedLetterIsSent(copiedLetterIsSent)
                .build();
    }
}
