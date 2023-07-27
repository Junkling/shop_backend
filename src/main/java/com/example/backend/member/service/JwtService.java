package com.example.backend.member.service;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String getToken(String key, Object value);
    Claims getClaims(String token);
    boolean isValid(String token);

    Long getId(String token);
}
