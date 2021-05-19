package org.springframework.mvc.servlet;


import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//SpringMVC 核心控制器
public class DisPatcherServlet extends HttpServlet {

    public DisPatcherServlet(){
        System.out.println("servlet 对象创建。。。");
    }

    @Override
    public void init() throws ServletException {

        System.out.println("servlet 初始化");

        String contextConfigLocation = this.getServletConfig().getInitParameter("contextConfigLocation");
        System.out.println("springmvc的配置文件：" + contextConfigLocation);

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.service(req, resp);
        System.out.println("servlet 方法。。。。");
    }
}
