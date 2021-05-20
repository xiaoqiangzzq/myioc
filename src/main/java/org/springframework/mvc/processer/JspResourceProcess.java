package org.springframework.mvc.processer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jsp资源处理
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/19 17:12
 * Copyright 2021 by WiteMedia
 */
public class JspResourceProcess implements ProcesserChain {

    private String prefix = "/WEB-INF/jsp/";
    private String suffix = ".jsp";

    @Override
    public boolean process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String requestURI = httpServletRequest.getRequestURI();
        if(requestURI.endsWith(suffix)){
            //将jsp交给tomcat处理
            RequestDispatcher dispatcher = httpServletRequest.getServletContext().getRequestDispatcher("jsp");
            try {
                httpServletResponse.setStatus(200);
                dispatcher.forward(httpServletRequest,httpServletResponse);
                return false;
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
