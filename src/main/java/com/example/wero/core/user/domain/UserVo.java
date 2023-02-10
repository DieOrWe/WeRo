package com.example.wero.core.user.domain;

import lombok.Getter;

/**
 * UserRestController 에 findPw 메소드에서 사용하기 위한 객체
 * 값을 전달하기만 하는 Value Object
 * 함 만들어봄
 */
@Getter
public class UserVo {
    private String userId;
    private String userEmail;
    private String userPw;
}
