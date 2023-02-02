package com.example.wero.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

import java.util.Date;

// 토큰 생성, 만료  클래스
public class JwtUtil {

    // 유저id 가져오기
    public static String getUserName(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userId", String.class);
    }

    
    // 만료
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    
    // 생성
    public static String createJwt(String userName, String secreteKey, Long expiredMs){
        Claims claims = Jwts.claims();
        claims.put("userName", userName);

        return Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis())) //토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료시간
                .signWith(SignatureAlgorithm.HS256, secreteKey) // H MAC-SHA-256이고, 256bits(32bytes)인 digest 를 생성
                .compact();
    }

}
