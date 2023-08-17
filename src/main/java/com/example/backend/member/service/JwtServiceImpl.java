package com.example.backend.member.service;

import com.example.backend.member.auth.PrincipalDetailsService;
import com.example.backend.member.auth.TokenInfo;
import io.jsonwebtoken.*;
import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final PrincipalDetailsService principalDetailsService;
    @Override
    public TokenInfo getToken(Authentication authentication) {
        Date expTime = new Date();
        expTime.setTime(expTime.getTime() + 1000 * 60 * 30);
        Key signKey = setSignKey();
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(expTime)
                .signWith(signKey, SignatureAlgorithm.HS256).compact();
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(new Date().getDate() + 86400000))
                .signWith(signKey, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
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
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(setSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    @Override
    public Long getId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(setSignKey())
                .parseClaimsJws(token)
                .getBody();
        return claims == null ? 0 : principalDetailsService.getId(claims.getSubject());
    }
    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(this.getUserPk(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(setSignKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
