package com.example.backend.member.controller;

import com.example.backend.member.dto.LoginDto;
import com.example.backend.member.dto.MemberDto;
import com.example.backend.member.dto.MemberRequest;
import com.example.backend.member.service.JwtServiceImpl;
import com.example.backend.member.service.MemberService;
import com.example.backend.validator.MemberValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final MemberService memberService;
    private final JwtServiceImpl jwtService;
    private final MemberValidator memberValidator;
    private final ObjectMapper objectMapper;

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> param, HttpServletResponse res) throws Exception {
        LoginDto loginDto = new LoginDto(param.get("email"), param.get("password"));

        MemberDto memberDto = memberService.login(loginDto);
        String token = jwtService.getToken(memberDto);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);
        return new ResponseEntity<>(memberDto, HttpStatus.OK);
    }

    @PostMapping("/api/account/signup")
    public ResponseEntity signup(@Valid @RequestBody MemberRequest res, BindingResult bindingResult) throws Exception {
        memberValidator.validate(res, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(objectMapper.writeValueAsString(bindingResult), HttpStatus.BAD_REQUEST);
        }

        MemberDto memberDto = memberService.signup(res);
        return new ResponseEntity(memberDto, HttpStatus.OK);
    }

    @PostMapping("/api/account/logout")
    public ResponseEntity logout(HttpServletResponse res) {
        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        res.addCookie(cookie);
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
        log.info("token={}", token);
        Claims claims = jwtService.getClaims(token);
        if (claims != null) {
            long id = Long.parseLong(claims.get("id").toString());
            log.info("id={}", id);
            MemberDto dto = memberService.findById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        log.info("null");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}

