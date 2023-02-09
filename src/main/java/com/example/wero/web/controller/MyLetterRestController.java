package com.example.wero.web.controller;

import com.example.wero.core.myletter.application.MyLetterEditor;
import com.example.wero.core.myletter.application.MyLetterFinder;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/myLetter")
public class MyLetterRestController {
    private final MyLetterEditor editor;

    public MyLetterRestController(MyLetterFinder finder, MyLetterEditor editor, MyLetterRepository myLetterRepository) {
        this.editor = editor;
    }

    @PostMapping(path = "/createMyLetter")
    public String createMyLetter(@RequestBody MyLetterDTO myLetterDTO) {
        return editor.createMyLetter(myLetterDTO);
    }

    @DeleteMapping
    public String deleteMyLetter(@RequestBody String myLetterId, @RequestParam String writerId) {
        return editor.deleteMyLetter(myLetterId, writerId);
    }

}