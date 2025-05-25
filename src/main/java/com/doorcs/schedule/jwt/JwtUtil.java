package com.doorcs.schedule.jwt;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class JwtUtil {

    private final SecretKey key = Keys.hmacShaKeyFor(
        "61da679034fee316bff8aa5f5fdcee0e2df1787af393b904bc43242f9993ef8b".getBytes()
    ); // 간단한 구현을 위해 환경변수 분리 없이 키 값을 하드코딩

    public String createJwt(Long userId) {
        return Jwts.builder()
            .claim("userId", userId.toString())
            .signWith(key)
            .compact();
    }

    public Long getUserId(String token) {
        return Jwts.parser()
            .verifyWith(key) // 검증
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("userId", Long.class); // 페이로드에서 사용자 ID만 추출
    }
}
