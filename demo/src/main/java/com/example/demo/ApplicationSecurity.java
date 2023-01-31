package com.example.demo;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@EnableWebSecurity
public class ApplicationSecurity {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.anyRequest().permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
