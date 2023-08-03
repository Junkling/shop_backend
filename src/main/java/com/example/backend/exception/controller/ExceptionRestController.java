package com.example.backend.exception.controller;

import com.example.backend.exception.CustomException;
import com.example.backend.exception.service.ExceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception-example")
@RequiredArgsConstructor

public class ExceptionRestController {
    private final ExceptionService exceptionService;

    @GetMapping("/throw-my-exception/login")
    public void throwMyExceptionLogin() {
        // ex) 로그인 시 username에 해당하는 User가 없는 경우
        exceptionService.login();
    }

    @GetMapping("/throw-my-exception/join")
    public void throwMyExceptionJoin() {
        // ex) 회원가입 시 username이 중복되는 경우
        exceptionService.join();
    }

    @GetMapping("/throw-my-exception/db")
    public void throwMyExceptionDB() {
        // ex) 댓글 추가 시 DB 에러가 발생한 경우
        exceptionService.editComment();
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> myExceptionHandler(CustomException e) {
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(e.toDto());
    }
}

