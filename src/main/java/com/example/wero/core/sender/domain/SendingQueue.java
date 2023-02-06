package com.example.wero.core.sender.domain;

import com.example.wero.core.senduser.domain.SendUser;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "SendingQueue")
public class SendingQueue {
    @Id
    @Column(name = "index")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int index;

    @Column(name = "sending_Letter")
    private SendUser
}
