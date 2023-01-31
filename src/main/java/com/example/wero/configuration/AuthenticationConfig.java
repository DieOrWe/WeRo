package com.example.wero.configuration;

import com.example.wero.core.user.application.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthenticationConfig {

    private final UserManager userManager;
    @Value("${jwt.secret}")
    private String secretKey;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable() // 토큰인증
                .csrf().disable() // csrf 보안
                .cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE,"/api/user"). authenticated() // HttpMethod
//                .antMatchers("/api/user/**"). authenticated()//.authenticated() 인가받을때만 가능
                .antMatchers("/api/**").permitAll() // permitAll() 모든기능 기능
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// jwt 토큰 사용하는경우 쓴다고함
                .and()
                .addFilterBefore(new JwtFilter(userManager, secretKey), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
