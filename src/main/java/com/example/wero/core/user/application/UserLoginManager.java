package com.example.wero.core.user.application;

import com.example.wero.core.user.domain.User;

public interface UserLoginManager {
    String checkPw(String id, String pw);
}
