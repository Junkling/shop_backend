package com.example.backend.member.controller;

import com.example.backend.member.service.JwtServiceImpl;
import com.example.backend.member.service.MemberService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    private final MemberService memberService;
    private final JwtServiceImpl jwtService;

    @PostMapping("/api/account/login")
    public ResponseEntity login(@RequestBody Map<String, String> param, HttpServletResponse res) {
        log.info("email={}, password={}", param.get("email"), param.get("password"));
        Long memberId = memberService.findIdByEmailAndPassword(param.get("email"), param.get("password"));
        String token = jwtService.getToken("id", memberId);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);
        return new ResponseEntity<>(memberId, HttpStatus.OK);
    }

    @GetMapping("/api/account/check")
    public ResponseEntity check(@CookieValue(value = "token", required = false) String token) {
        log.info("token={}", token);
        Claims claims = jwtService.getClaims(token);
        if (claims != null) {
            long id = Long.parseLong(claims.get("id").toString());
            log.info("id={}", id);
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
        log.info("null");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}

