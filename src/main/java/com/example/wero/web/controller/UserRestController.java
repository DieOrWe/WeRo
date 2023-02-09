package com.example.wero.web.controller;

import com.example.wero.core.user.application.UserEditor;
import com.example.wero.core.user.application.UserFinder;
import com.example.wero.core.user.domain.UserDTO;
import com.example.wero.core.user.domain.UserVo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/user")
public class UserRestController {


    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${key.spring.security.oauth2.client.registration.google.client-id")
    private String googleClientId;

    private final UserFinder finder;
    private final UserEditor editor;

    public UserRestController(UserFinder finder, UserEditor editor) {
        this.finder = finder;
        this.editor = editor;
    }

    @PostMapping("/admin/IdPw/{userId}")
    public Boolean checkPw(@PathVariable String userId, @RequestBody String userPw) {
        return finder.checkPw(userId, userPw);
    }


    @GetMapping("/admin")
    public List<UserDTO> findAll() {
        return finder.findAll();
    }


    @GetMapping("/{userId}")
    public UserDTO findUser(@PathVariable String userId) {
        return finder.findUser(userId);
    }


    @PostMapping("/login")
    public String loginUser(@RequestBody UserDTO loginUser) {
        return finder.loginUser(loginUser);
    }

    @PostMapping
    public String createUser(@RequestBody UserDTO newUser) {
        return editor.createUser(newUser);
    }


    @PutMapping("/data")
    public String updateUser(@RequestBody UserDTO updateUser) {
        return editor.updateUser(updateUser);
    }


    @PostMapping("/data/updateWord/{userId}")
    public String updateUserPw(@PathVariable String userId, @RequestBody String changePw) {
        return editor.updateUserPw(userId, changePw);
    }
    //    변경 -> 회원정보 확인(checkPw) -> 진짜 변경하는 부분(updateUserPw)


    @DeleteMapping("/data/{userId}")
    public String deleteUser(@PathVariable String userId, @RequestBody String userPw) {
        return editor.deleteUser(userId, userPw);
    }

    @PostMapping("/data/findId")
    public String findId(@RequestBody String userEmail) {
        return finder.findId(userEmail.substring(1, userEmail.length() - 1));
    }

    @PostMapping("/data/findPw") // userPw 는 새로 바꿀 Pw를 받음.
    public String findPw(@RequestBody UserVo userVo) {
        return finder.findPw(userVo);
    }

    @RequestMapping(value = "/getGoogleAuthUrl")
    public @ResponseBody String getGoogleAuthUrl(HttpServletRequest request) throws Exception {

        String reqUrl = "https://accounts.google.com" + "/o/oauth2/v2/auth?client_id=" + googleClientId + "&redirect_uri=" +
                "http://localhost:8080/login/"
                + "&response_type=code&scope=email%20profile%20openid&access_type=offline";

        return reqUrl;
    }
}
