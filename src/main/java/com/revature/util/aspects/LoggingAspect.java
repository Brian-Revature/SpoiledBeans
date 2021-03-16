package com.revature.util.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * This aspect logs all events that happen in the running application
 */
@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LogManager.getLogger(LoggingAspect.class);

    /**
     * A generalized point cut to make annotating methods in this class easier
     */
    @Pointcut("within(com.revature..*) && !within(com.revature.web.filters..*)")
    public void logAllPointcut(){}

    /**
     * Before the method, execute this advice
     * @param jp the method that will have the advice run before
     */
    @Before("logAllPointcut()")
    public void logMethodStart(JoinPoint jp){
        String methodSig = extractMethodSignature(jp);
        String argStr = Arrays.toString(jp.getArgs());
        logger.info("{} invoked at {}; input arguments: {}", methodSig, LocalDateTime.now(), argStr);
    }

    /**
     * If the method returns correctly, run this advice
     * @param jp the method returning
     * @param returned the object returned from the method
     */
    @AfterReturning(pointcut = "logAllPointcut()", returning = "returned")
    public void logMethodReturn(JoinPoint jp, Object returned){
        String methodSig = extractMethodSignature(jp);
        logger.info("{} successfully returned at {} with a value of {}", methodSig, LocalDateTime.now(), returned);
    }

    /**
     * If the method throws an exception, run this advice
     * @param jp the method that threw the exception
     * @param e the exception thrown by the method
     */
    @AfterThrowing(pointcut = "logAllPointcut()", throwing = "e")
    public void logErrorOccurring(JoinPoint jp, Exception e){
        String methodSig = extractMethodSignature(jp);
        logger.error("{} was thrown in method {} at {} with message: {}", e.getClass().getSimpleName(), methodSig, LocalDateTime.now(), e.getMessage());
    }

    /**
     * This method gets the full method signature
     * @param jp the method to get the signature
     * @return the string representation of the method signature
     */
    private String extractMethodSignature(JoinPoint jp) {
        return jp.getTarget().getClass().toString() + "." + jp.getSignature().getName();
    }
}
