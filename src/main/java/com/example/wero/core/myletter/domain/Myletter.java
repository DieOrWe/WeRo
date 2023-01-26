package com.example.wero.core.myletter.domain;

import javax.persistence.Column;
import javax.persistence.Id;

public class Myletter {

    @Column(name = "mail_id")
    @Id
    private int mailId; // 편지의 아이디

    @Column(name = "user_id")
    private String userId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private String createdAt;

    @
}
