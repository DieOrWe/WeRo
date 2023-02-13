package com.example.wero.core.websocket.domain;

import com.example.wero.core.receiveduser.domain.ReceivedUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class BackMessage {

    private String content;

 
}