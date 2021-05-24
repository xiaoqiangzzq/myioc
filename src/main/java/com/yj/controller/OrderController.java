package com.yj.controller;

import com.yj.bean.Order;
import com.yj.service.OrderService;
import org.springframework.annotation.Autowired;
import org.springframework.annotation.Controller;
import org.springframework.mvc.annotation.RequestBody;
import org.springframework.mvc.annotation.RequestMapping;
import org.springframework.mvc.annotation.RequestParam;
import org.springframework.web.bind.RequestMehtod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/8 15:08
 * Copyright 2021 by WiteMedia
 */
@Controller(value = "oc")
@RequestMapping("/order")
public class OrderController {

    @Autowired(value = "os1")
    OrderService orderService;

    @RequestBody
    @RequestMapping(value = "/getOrders")
    public String  getOrders(){
        System.out.println("调用Controller。。。。");
        List<Order> orders = orderService.getOrders();
        for (Order order : orders) {
            System.out.println(order.getTitle());
        }
        return "调用成功";
    }


    @RequestMapping(value = "/list",method = RequestMehtod.GET)
    public String list(int age ,@RequestParam(name = "uname") String name){

        //httpServletRequest.setAttribute("name","zzq");
        //默认转发存储值
        return "show";         //跳转到show.jsp /WEB-INF/jsp/show.jsp

    }

    public void add(){
        orderService.addOrder(new Order(111,"杯子",62));
    }


    public void transferMoney(){
        orderService.transferMoney("666","777",2000);
    };
}
