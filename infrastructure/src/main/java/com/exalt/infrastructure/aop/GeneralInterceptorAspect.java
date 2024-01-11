package com.exalt.infrastructure.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Slf4j
@Component
public class GeneralInterceptorAspect {

    @Pointcut("execution(* com.exalt.infrastructure.adapter.in.rest.controller.*.*(..))")
    public void loggingPointCut(){}

    @Around("loggingPointCut()")
    public Object loggingAdvice(ProceedingJoinPoint p){
        try {
            String args = String.format("(%s)",p.getArgs());
            log.info(String.format("####### Execution %s",p.getSignature().toShortString().replace("(..)",args)));
            return p.proceed(p.getArgs());
        } catch (Throwable e) {
            log.error(String.format("####### %s",e.getMessage()));
            throw new RuntimeException(e);
        }
    }
}
