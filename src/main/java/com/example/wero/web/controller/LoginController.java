package com.example.wero.web.controller;

import com.example.wero.core.user.application.UserLoginManager;
import com.example.wero.core.user.domain.User;
import com.example.wero.core.user.domain.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="api/userLogin")
public class LoginController {
    private final UserLoginManager userLoginManager;

    public LoginController(UserLoginManager userLoginManager) {
        this.userLoginManager = userLoginManager;
    }

    @PostMapping
    public String checkPw(@RequestBody UserDTO userDTO) {
        System.out.println("checkPW() called");
        return userLoginManager.checkPw(userDTO.getUserId(), userDTO.getUserPw());
    }

}
