package com.yj.aop;

import org.springframework.annotation.Around;
import org.springframework.annotation.Aspect;
import org.springframework.aop.ProceedingJoinPoint;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/13 15:10
 * Copyright 2021 by WiteMedia
 */
@Aspect
public class LogAop {

    @Around(execution = "com.yj.service.impl.OrderServiceImpl2.addOrder")
    public Object around(ProceedingJoinPoint proceedingJoinPoint){

        Object result = null;
        try {
            System.out.println("前置通知。。。。");
            result = proceedingJoinPoint.proceed();
            System.out.println("返回通知。。。。");
        }catch (Exception e){
            System.out.println("异常通知。。。。");
        }finally {
            System.out.println("后置通知。。。。");
        }
        return result;
    }
}
