package com.example.wero.web.controller;

import com.example.wero.core.myletter.application.MyLetterEditor;
import com.example.wero.core.myletter.application.MyLetterFinder;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/myLetter")
public class MyLetterRestController {
    private final MyLetterFinder finder;
    private final MyLetterEditor editor;
    private final MyLetterRepository myLetterRepository;

    public MyLetterRestController(MyLetterFinder finder, MyLetterEditor editor, MyLetterRepository myLetterRepository) {
        this.finder = finder;
        this.editor = editor;
        this.myLetterRepository = myLetterRepository;
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
