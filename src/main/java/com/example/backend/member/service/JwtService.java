package com.example.backend.member.service;

import com.example.backend.member.auth.TokenInfo;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

public interface JwtService {
    TokenInfo getToken(Authentication authentication);
    Claims getClaims(String token);
    void isValid(String token);

    Long getId(String token);
}
