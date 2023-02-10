package com.example.wero.core.jwtutils;

import com.example.wero.core.user.application.UserManager;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 로그인시 발급받은 Jwt 토큰을 확인하는 (인증)
// 이후 권한을 확인하는 (인가) 과정

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final UserManager userManager;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);


        //Bearer JWT 혹은 OAuth 에 대한 토큰을 사용한다. (RFC 6750)
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        // Token 꺼내기
        String token = authorization.split(" ")[1];
//        log.info("Realtoken:{}", token);
//        log.info("secretKey:{}", secretKey);


        // Token expired (만료)확인
        if (JwtUtil.isExpired(token, secretKey)) {
            log.error("Token 이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }


        // Token 에서 userID 꺼내기
        String UserId = JwtUtil.getUserId(token, secretKey);
        log.info("UserId:{}", UserId);

        // Token 에서 userNickName 꺼내기
//        String UserNickName = JwtUtil.getUserNickName(token, secretKey);
//        log.info("UserNickName:{}", UserNickName);

        // 프론트에서 전송된 Jwt 토큰
//        String RequestJwt = JwtUtil.getJwt();
//        log.info("RequestJwt:{}", RequestJwt);


        // 권한부여
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(UserId, null, List.of(new SimpleGrantedAuthority("USER")));

        // Detail 넣음 (규칙)
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);

    }

}
