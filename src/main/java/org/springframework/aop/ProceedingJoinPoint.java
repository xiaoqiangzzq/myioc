package org.springframework.aop;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 连接点
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/13 15:17
 * Copyright 2021 by WiteMedia
 */
public class ProceedingJoinPoint {

    //目标对象
    private Object target;

    //目标方法
    private Method method;

    //目标参数
    private Object[] args;

    public ProceedingJoinPoint(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    public Object proceed(){

        try {
            return method.invoke(target,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

}
