package com.example.wero.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Aspect
@Slf4j
@Component
public class LoggingAspect {
    @Before(value = "execution(* com.example.wero.core.**.**.*Manager.*(..))")
    public void log(JoinPoint joinPoint) {
        log.info("Entering {}, {}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("args[{}] --> ", arg);
        }
    }

}
