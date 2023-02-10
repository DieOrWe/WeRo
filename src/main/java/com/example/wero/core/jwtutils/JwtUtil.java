package com.example.wero.core.jwtutils;

import com.example.wero.core.user.domain.UserDTO;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
// 토큰 생성, 만료  클래스
public class JwtUtil {

    // 유저 Id 가져오기
    public static String getUserId(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("Id", String.class);
    }


    // 유저 NickName 가져오기
    public static String getUserNickName(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("NickName", String.class);
    }


    // 만료 확인
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }


    // 생성 Jwt
    public static String createJwt(UserDTO loginUser, String secreteKey, Long expiredMs) {
        return Jwts.builder()
                .setHeader(createHeader()) // 헤더 추가
                .setClaims(createClaims(loginUser)) // 유저정보 추가
                .signWith(SignatureAlgorithm.HS256, secreteKey)  // H MAC-SHA-256이고, 256bits(32bytes)인 digest 를 생성
                .setIssuedAt(new Date(System.currentTimeMillis())) //토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료시간
                .compact();
    }

    // Header 추가를 위한 해쉬맵
    private static Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }

    // Claim 추가를 위한 해쉬맵
    private static Map<String, Object> createClaims(UserDTO loginUser) { // payload
        Map<String, Object> claims = new HashMap<>();
        claims.put("NickName", loginUser.getUserNickName());
        claims.put("Id", loginUser.getUserId());
        return claims;
    }

    // 헤더에서 JWT 추출 (유효성검증용)
    public static String getJwt() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

}
