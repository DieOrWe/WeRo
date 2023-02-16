package com.example.wero.web.controller;

import com.example.wero.core.myletter.application.MyLetterEditor;
import com.example.wero.core.myletter.application.MyLetterFinder;
import com.example.wero.core.myletter.domain.MyLetterDTO;
import com.example.wero.core.myletter.infrastructure.MyLetterRepository;

import io.swagger.annotations.Api;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    
    @Scheduled(cron = "* * 00 * * ?") // 시스템 시간을 기준으로 매일 00시 00분 00초에 실행됨.
    public String deleteMyLetter() {
        return editor.deleteMyLetter();
    }

}
