package org.springframework.mvc.servlet;


import org.springframework.container.ClassPathXmlApplicationContext;
import org.springframework.mvc.processer.ControllerProcess;
import org.springframework.mvc.processer.EncodingProcess;
import org.springframework.mvc.processer.ExecuteProcess;
import org.springframework.mvc.processer.JspResourceProcess;
import org.springframework.mvc.processer.ProcesserChain;
import org.springframework.mvc.processer.StaticResourceProcess;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//SpringMVC 核心控制器
public class DisPatcherServlet extends HttpServlet {

    //处理的责任链
    private List<ProcesserChain> processerChainList = new ArrayList<>();

    public DisPatcherServlet(){
        System.out.println("servlet 对象创建。。。");
    }

    @Override
    public void init() throws ServletException {

        System.out.println("servlet 初始化");

        String contextConfigLocation = this.getServletConfig().getInitParameter("contextConfigLocation");
        System.out.println("springmvc的配置文件：" + contextConfigLocation);


        ClassPathXmlApplicationContext instance = ClassPathXmlApplicationContext.getInstance();
        //控制器集合
        Map<Class<?>, Object> controllerClassContainer = instance.getControllerClassContainer();

        System.out.println("传入：" + controllerClassContainer);

        processerChainList.add(new EncodingProcess());
        processerChainList.add(new StaticResourceProcess(contextConfigLocation.split(":")[1]));
        processerChainList.add(new JspResourceProcess());
        processerChainList.add(new ControllerProcess(controllerClassContainer));
    }

    /**
     * 每执行一次方法，调用一次
     *
     * @param req
     * @param resp
     * @return void
     * @author Zeng zhiqiang 2021/5/19 16:08
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.service(req, resp);
        System.out.println("servlet 方法。。。。");

        //用户的请求路径
        //过滤 = 静态资源 mp4,css ->jsp->动态资源 order/showOrders
        ExecuteProcess executeProcess = new ExecuteProcess(processerChainList,req,resp);
        executeProcess.execute();
    }
}
