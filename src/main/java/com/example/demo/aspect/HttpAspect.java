package com.example.demo.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {


    public  final static Logger logger= LoggerFactory.getLogger(HttpAspect.class);
    @Before("execution(public * com.example.demo.controller.TUserController.addUser(..))")
    public  void log(){
       logger.info("hhhh");
    }


    @Pointcut("execution(public * com.example.demo.controller.TUserController.*(..))")
    public  void logtest(){

    }


    @Before("logtest()")
    public  void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
         HttpServletRequest request =attributes.getRequest();

         logger.info("URL={}",request.getRequestURI());


         logger.info("method={}",request.getMethod());


         logger.info("IP={}",request.getRemoteAddr());

         logger.info("class_medthod={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());


         logger.info("args={}",joinPoint.getArgs());

        logger.info("dobefore");
    }


    @After("logtest()")
    public  void doAfter(){

        logger.info("doafter");
    }


    @AfterReturning(returning = "object",pointcut = "logtest()")
    public void doAfterReturn(Object object){

//        logger.info("response={}",object.toString());
    }

}


