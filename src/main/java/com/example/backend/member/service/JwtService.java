package com.example.backend.member.service;

import com.example.backend.member.dto.MemberDto;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String getToken(MemberDto memberDto);
    Claims getClaims(String token);
    void isValid(String token);

    Long getId(String token);
}
