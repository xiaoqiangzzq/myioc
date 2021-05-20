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

    private RequestPath requestPath;

    private Method method;

    public HandlerMapping(RequestPath requestPath, Method method) {
        this.requestPath = requestPath;
        this.method = method;
    }

    public RequestPath getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(RequestPath requestPath) {
        this.requestPath = requestPath;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
