package com.example.wero.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    public static String createJwt(String userId, String secreteKey, Long expiredMs){
        Claims claims = Jwts.claims();
        claims.put("userId", userId);

        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis())) //토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료시간
                .signWith(SignatureAlgorithm.HS256, secreteKey) // H MAC-SHA-256이고, 256bits(32bytes)인 digest 를 생성
                .compact();
    }
}
