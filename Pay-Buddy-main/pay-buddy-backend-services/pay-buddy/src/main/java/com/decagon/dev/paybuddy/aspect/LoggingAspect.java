package com.decagon.dev.paybuddy.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Ikechi Ucheagwu
 * @created 09/03/2023 - 00:43
 * @project Pay-Buddy
 */

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Around(value = "targetVTPassMethods()")
    public Object loggingAdviceAroundVTPassMethod(ProceedingJoinPoint proceedingJoinPoint) {
        Object returnValue = null;

        try {
            log.info("Before around advice " + proceedingJoinPoint.getSignature().getName() + " is called.");
            returnValue = proceedingJoinPoint.proceed();
            log.info("After returned around advice " + proceedingJoinPoint.getSignature().getName() +
                    " is called. Target object is: {}", proceedingJoinPoint.getTarget());
        } catch (Throwable e) {
            log.error(e.getMessage());
            log.error("After throwing around advice " + proceedingJoinPoint.getSignature().getName() +
                    " is called. Target object is: {}", proceedingJoinPoint.getTarget());
        }

        log.info("After finally around advice " + proceedingJoinPoint.getSignature().getName() +
                " is called. Target object is: {} ", proceedingJoinPoint.getTarget());

        return returnValue;
    }

    @Pointcut(value = "within(com.decagon.dev.paybuddy.services.vtpass..*)")
    public void targetVTPassMethods(){};
}
