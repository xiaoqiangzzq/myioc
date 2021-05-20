package org.springframework.mvc.servlet;

import org.springframework.web.bind.RequestPath;

import java.lang.reflect.Method;

/**
 * 请求映射器
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/20 18:55
 * Copyright 2021 by WiteMedia
 */
public class HandlerMapping {

    private Object controllerObject ;//控制对象

    private Method method;//控制请求的方法

    public HandlerMapping(Object controllerObject, Method method) {
        this.controllerObject = controllerObject;
        this.method = method;
    }

    public Object getControllerObject() {
        return controllerObject;
    }

    public void setControllerObject(Object controllerObject) {
        this.controllerObject = controllerObject;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
