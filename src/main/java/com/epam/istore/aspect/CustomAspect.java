package com.epam.istore.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;


@Aspect
@Component
public class CustomAspect {

//    @Before("execution(* com.epam.istore.resources.MainPageResource.*(..))")
//    public void before(JoinPoint joinPoint) {
//        System.out.println(joinPoint.getSignature());
//        System.out.println("FUCK");
//    }
}
