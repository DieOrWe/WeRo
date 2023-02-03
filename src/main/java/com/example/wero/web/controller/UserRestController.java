package com.example.wero.web.controller;

import com.example.wero.core.user.application.UserEditor;
import com.example.wero.core.user.application.UserFinder;
import com.example.wero.core.user.domain.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/user")
public class UserRestController {


    public UserRestController(UserFinder finder, UserEditor editor) {
        this.finder = finder;
        this.editor = editor;
    }

    private final UserFinder finder;
    private final UserEditor editor;


    @GetMapping("/admin")
    public List<UserDTO> findAll() {
        System.out.println("findAll() called");
        return finder.findAll();
    }


    @GetMapping("/{userId}") // localhost:8080/api/user/guguttemi <- 이렇게 받을거임
    public UserDTO findUser(@PathVariable String userId) { // @PathVariable 이렇게 해야 아이디 값을 받아올 수 있음. -> url 변수의 경우
        // @PathVariable 에 대한 추가 정보 ref) https://leeborn.tistory.com/entry/Spring-PathVariable-%EA%B8%B0%EB%B3%B8%EA%B0%92-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0
        return finder.findUser(userId);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody UserDTO loginUser) {
        System.out.println("loginUser() called");
        return finder.loginUser(loginUser);
    }

    @PostMapping
    @ResponseBody
    public String createUser(@RequestBody UserDTO newUser) {
        System.out.println("createUser() called");
        return editor.createUser(newUser);
    }


    @PutMapping("/data")
    public String updateUser(@RequestBody UserDTO updateUser) {
        return editor.updateUser(updateUser);
    }

    @DeleteMapping("/data")
    public String deleteUser(@RequestBody String id, String pw){

        return editor.deleteUser(id, pw);
    }

}
