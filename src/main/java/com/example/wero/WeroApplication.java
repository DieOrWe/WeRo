package com.example.wero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude= {UserDetailsServiceAutoConfiguration.class})
public class 	WeroApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeroApplication.class, args);
	}

}
