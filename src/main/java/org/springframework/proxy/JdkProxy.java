package org.springframework.proxy;

import org.springframework.aop.ProceedingJoinPoint;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/13 16:43
 * Copyright 2021 by WiteMedia
 */
public class JdkProxy<T> {

    //目标类
    Class<?> targetClass ;

    //切面类对象
    Class<?> aopClass;

    //目标对象
    Object targetObject;

    //要被代理的方法名字
    String methodName;

    //AOP切面类的方法
    Method aopMehod;


    public JdkProxy(Class<?> targetClass, Class<?> aopClass, Object targetObject, String methodName, Method aopMehod) {
        this.targetClass = targetClass;
        this.aopClass = aopClass;
        this.targetObject = targetObject;
        this.methodName = methodName;
        this.aopMehod = aopMehod;
    }

    public Object getProxyInstance(){
        Object o = Proxy.newProxyInstance(targetClass.getClassLoader(), targetClass.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //调用目标对象目标方法
                if (methodName.equals(method.getName())) {
                    ProceedingJoinPoint joinPoint = new ProceedingJoinPoint(targetObject, method, args);
                    return aopMehod.invoke(aopClass.newInstance(), joinPoint);
                } else {
                    return method.invoke(targetObject, args);
                }
            }
        });
        return o;
    }
}
