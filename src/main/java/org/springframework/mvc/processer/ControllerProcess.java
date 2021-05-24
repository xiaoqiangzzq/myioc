package org.springframework.mvc.processer;

import org.springframework.mvc.annotation.RequestMapping;
import org.springframework.mvc.servlet.HandleAdapter;
import org.springframework.mvc.servlet.HandlerMapping;
import org.springframework.web.bind.RequestMehtod;
import org.springframework.web.bind.RequestPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/19 17:12
 * Copyright 2021 by WiteMedia
 */
public class ControllerProcess implements ProcesserChain {

    public Map<Class<?>, Object> controllerClassContainer;

    //请求地址 请求对象方法
    public Map<RequestPath,HandlerMapping> mappingMap = new HashMap<>();

    public ControllerProcess(Map<Class<?>, Object> controllerClassContainer) {
        this.controllerClassContainer = controllerClassContainer;
    }

    @Override
    public boolean process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        if(controllerClassContainer.size() > 0){

            System.out.println("当前：" + controllerClassContainer);
            Set<Map.Entry<Class<?>, Object>> entries = controllerClassContainer.entrySet();

            for (Map.Entry<Class<?>, Object> entry : entries) {
                //控制器类
                Class<?> c = entry.getKey();
                //控制器对象
                Object oc = entry.getValue();
                boolean b = c.isAnnotationPresent(RequestMapping.class);
                Method[] methods = c.getDeclaredMethods();
                for (Method method : methods) {

                    StringBuilder ulr = new StringBuilder();
                    if(Boolean.TRUE.equals(b)){
                        RequestMapping requestMapping = c.getAnnotation(RequestMapping.class);
                        String value = requestMapping.value();
                        value = value.startsWith("/")?value:"/"+value;
                        ulr.append(value);
                    }

                    boolean b1 = method.isAnnotationPresent(RequestMapping.class);
                    if(Boolean.TRUE.equals(b1)){
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        String value = requestMapping.value();
                        value = value.startsWith("/")?value:"/"+value;
                        ulr.append(value);
                        System.out.println("控制器中方法的访问地址："+ ulr.toString());
                        //解析方法的请求方式
                        RequestMehtod requestMethod = requestMapping.method();
                        RequestPath requestPath = new RequestPath(ulr.toString(),requestMethod.name());
                        //请求对象
                        HandlerMapping handlerMapping = new HandlerMapping(oc,method);
                        mappingMap.put(requestPath,handlerMapping);
                    }
                }
            }
        }
        //处理请求
        String requestURI = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod();
        RequestPath requestPath = new RequestPath(requestURI,method);
        HandlerMapping handlerMapping = mappingMap.get(requestPath);
        if(handlerMapping != null){

            //映射请求处理
            System.out.println("映射到用户请求：" + requestURI);
            HandleAdapter handleAdapter = new HandleAdapter(handlerMapping,httpServletRequest,httpServletResponse);
            try {
                handleAdapter.handle();
            } catch (Exception e) {
                try {
                    httpServletResponse.sendError(500,"server inner error");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }else {
            try {
                httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND,"not find your request");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("用户请求的地址404");
        }

        return false;
    }
}
