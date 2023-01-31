package com.example.wero.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable() // 토큰인증
                .csrf().disable() // csrf 보안
                .cors().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/api/user").permitAll() // permitAll() 모든기능 기능
                .antMatchers(HttpMethod.PUT,"/api/user"). authenticated()//.authenticated() 인가받을때만 가능
                .antMatchers(HttpMethod.DELETE,"/api/user"). authenticated()//.authenticated() 인가받을때만 가능
                .antMatchers("/api/user/**"). authenticated()//.authenticated() 인가받을때만 가능
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)// jwt 토큰 사용하는경우 쓴다고함
                .and()
                .build();
    }
}
