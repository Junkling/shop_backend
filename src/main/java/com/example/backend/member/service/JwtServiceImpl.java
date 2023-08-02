package com.example.backend.member.service;

import com.example.backend.config.auth.PrincipalDetails;
import com.example.backend.member.dto.MemberDto;
import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Override
    public String getToken(MemberDto memberDto) {
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30);
        Key signKey = setSignKey();
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put("id", memberDto.getId());
        map.put("email", memberDto.getEmail());
        map.put("role", memberDto.getRole());

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256);
        return builder.compact();
    }

    private Key setSignKey() {
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
        return signKey;
    }

    @Override
    public Claims getClaims(String token) {
        if (token != null && !token.equals("")) {
            try {
                return Jwts.parserBuilder().setSigningKey(setSignKey()).build().parseClaimsJws(token).getBody();
            } catch (ExpiredJwtException e) {
                //만료
            } catch (JwtException e) {
                //유효하지 않은 토큰
            }
        }
        return null;
    }

    @Override
    public void isValid(String token) {
        if(this.getClaims(token) == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);}

        return;
    }

    @Override
    public Long getId(String token) {
        Claims claims = this.getClaims(token);
        return claims == null ? 0 : Long.parseLong(claims.get("id").toString());
    }
    @SuppressWarnings("unchecked")
    public Authentication getAuthentication(String token) {

        // 토큰 기반으로 유저의 정보 파싱
        Claims claims = Jwts.parser().setSigningKey(setSignKey()).parseClaimsJws(token).getBody();

        Long userPk = claims.get("id", Long.class);
        String email = claims.get("email", String.class);
        PrincipalDetails userDetails = new PrincipalDetails(userPk, email);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
