package com.example.medic.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Log before service method
     */
    @Before(
            "execution(* com.example.medic.service.impl.*.*(..))"
    )
    public void logBefore(
            JoinPoint joinPoint
    ) {

        log.info(
                """
                ==============================
                [REQUEST]
                Method : {}
                Args   : {}
                ==============================
                """,

                joinPoint
                        .getSignature()
                        .getName(),

                Arrays.toString(
                        joinPoint.getArgs()
                )
        );
    }

    /**
     * Log success
     */
    @AfterReturning(

            pointcut =
                    "execution(* com.example.medic.service.impl.*.*(..))",

            returning =
                    "result"
    )
    public void logAfterReturning(

            JoinPoint joinPoint,

            Object result
    ) {

        log.info(
                """
                ==============================
                [SUCCESS]
                Method   : {}
                Response : {}
                ==============================
                """,

                joinPoint
                        .getSignature()
                        .getName(),

                result
        );
    }

    /**
     * Log exception
     */
    @AfterThrowing(

            pointcut =
                    "execution(* com.example.medic.service.impl.*.*(..))",

            throwing =
                    "exception"
    )
    public void logAfterThrowing(

            JoinPoint joinPoint,

            Exception exception
    ) {

        log.error(
                """
                ==============================
                [ERROR]
                Method  : {}
                Message : {}
                ==============================
                """,

                joinPoint
                        .getSignature()
                        .getName(),

                exception.getMessage()
        );
    }
}