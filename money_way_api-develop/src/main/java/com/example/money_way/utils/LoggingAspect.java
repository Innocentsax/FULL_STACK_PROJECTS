package com.example.money_way.utils;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs any method in the services package before it is executed
     * @param joinPoint: has the details of the method being executed
     */
    @Before("execution (* com.example.money_way.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getName() + " method started. [" +joinPoint.getSignature().getDeclaringTypeName()+"]");
    }

    /**
     * Logs any method in the services package that executes successfully
     * @param joinPoint: has the details of the method being executed
     */
    @AfterReturning("execution (* com.example.money_way.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getName() + " method successfully executed. [" +joinPoint.getSignature().getDeclaringTypeName()+"]");
    }

    @AfterReturning("execution (* com.example.money_way.utils.*.*(..))")
    public void logAfter1(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getName() + " method successfully executed. [" +joinPoint.getSignature().getDeclaringTypeName()+"]");
    }

    /**
     * Logs any method that created or updated an entity successfully
     * @param joinPoint: has the details of the method being executed
     */
    @AfterReturning("execution (* com.example.money_way.repository.*.save(..))")
    public void logEntityCreation(JoinPoint joinPoint) {
        logger.info(joinPoint.getSignature().getName() + " method completed successfully. [" +joinPoint.getSignature().getDeclaringTypeName()+"]");
    }
}
