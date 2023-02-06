package com.example.wero.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Slf4j
@Component
public class LoginAspect {
    @Before(value = "execution(* com.example.wero.core.**.**.*Manager.*(..))")
    public void log(JoinPoint joinPoint) {
        log.info("Entering {}, {}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            log.info("args[{}] --> ", args[i]);
        }
    }

}
