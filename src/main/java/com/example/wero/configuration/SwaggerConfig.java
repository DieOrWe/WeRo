package com.example.wero.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())   // 현재 RequestMapping 으로 할당된 모든 URL 리스트 추출
                .paths(PathSelectors.any())            // PathSelectors.any("/api/**")) 와 같이 /api/** 인 URL 로만 필터링 할 수 있습니다.
                .build();
    }
}