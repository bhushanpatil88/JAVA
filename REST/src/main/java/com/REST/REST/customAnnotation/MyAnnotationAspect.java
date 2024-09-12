package com.REST.REST.customAnnotation;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MyAnnotationAspect {
    @Around("@annotation(myAnnotation)")
    public Object proceed(ProceedingJoinPoint pjp,  MyAnnotation myAnnotation) throws Throwable {
            System.out.println("Enter the Aspect");
            return pjp.proceed();
    }

}
