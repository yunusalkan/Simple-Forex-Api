package com.yns.forex.simpleforexapi.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class CentralLoggingAspect {

    @Pointcut("execution(* com.yns.forex.simpleforexapi.controller..*(..))")
    public void controllerPointCut() {}

    @Pointcut("execution(* com.yns.forex.simpleforexapi.service..*(..))")
    public void servicePointCut() {}

    @Pointcut("execution(* com.yns.forex.simpleforexapi.service..*(..))")
    public void persistencePointCut() {}

    @AfterThrowing(value = "controllerPointCut() || servicePointCut() || persistencePointCut()", throwing = "ex")
    public void afterExceptionAdvice(JoinPoint jp, Exception ex) {
        String className = jp.getSignature().getDeclaringTypeName();
        String methodName = jp.getSignature().getName();
        log.error(String.format("Exception occurred at %s", className+"."+methodName+"()"));
        log.error(String.format("Exception message: %s", ex.getMessage(), ex));
    }
}
