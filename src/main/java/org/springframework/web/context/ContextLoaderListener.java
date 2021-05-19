package org.springframework.web.context;

import org.springframework.container.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//监听web项目启动的事件
public class ContextLoaderListener  implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String contextConfigLocation = servletContextEvent.getServletContext().getInitParameter("contextConfigLocation");
        String cf = contextConfigLocation.split(":")[1];
        System.out.println("web 应用启动，spring框架启动");
        ClassPathXmlApplicationContext context = ClassPathXmlApplicationContext.getInstance();
        context.bootStrap(cf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("web 应用停止");
    }
}
