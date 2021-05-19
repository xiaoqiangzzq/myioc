package org.springframework.mvc.processer;

import org.springframework.mvc.xml.SpringMvcPaser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/19 17:12
 * Copyright 2021 by WiteMedia
 */
public class StaticResourceProcess implements ProcesserChain {

    private String springMvcConfig;

    private List<String> staticList ;


    public StaticResourceProcess(String springMvcConfig) {
        this.springMvcConfig = springMvcConfig;
        staticList = SpringMvcPaser.getStaticResource(springMvcConfig);
    }

    @Override
    public boolean process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {


        //请求路径
        String requestURI = httpServletRequest.getRequestURI();
        if(null != staticList){
            for (String s : staticList) {
                if(requestURI.contains(s)){
                    //通过tomcat中Default的dispatcher，直接返回静态资源
                    RequestDispatcher dispatcher = httpServletRequest.getServletContext().getNamedDispatcher("default");
                    try {
                        dispatcher.forward(httpServletRequest,httpServletResponse);
                        //200 ok 响应状态码
                        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                    } catch (ServletException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            }
        }
        return false;
    }
}
