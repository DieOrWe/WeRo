package com.example.demo.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class UserController {

        @Autowired
        private UserRepository repo;

        @PostMapping
        public ResponseEntity<User> create(@RequestBody @Valid User user) {
            User savedUser = repo.save(user);
            URI productURI = URI.create("/users/" + savedUser.getUserName());
            return ResponseEntity.created(productURI).body(savedUser);
        }

        @GetMapping
        public List<User> list() {
            return repo.findAll();
        }
}
