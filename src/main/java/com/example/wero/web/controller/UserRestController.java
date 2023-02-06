package com.example.wero.web.controller;

import com.example.wero.core.user.application.UserEditor;
import com.example.wero.core.user.application.UserFinder;
import com.example.wero.core.user.domain.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/user")
public class UserRestController {


    private final UserFinder finder;
    private final UserEditor editor;

    public UserRestController(UserFinder finder, UserEditor editor) {
        this.finder = finder;
        this.editor = editor;
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

    @PutMapping("/data/updateWord")
    public String updateUserPw();

    @DeleteMapping("/data")
    public String deleteUser(@RequestBody String id, String pw) {
        return editor.deleteUser(id, pw);
    }

}
