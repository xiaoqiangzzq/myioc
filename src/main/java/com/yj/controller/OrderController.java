package com.yj.controller;

import com.yj.bean.Order;
import com.yj.service.OrderService;
import org.springframework.annotation.Autowired;
import org.springframework.annotation.Controller;
import org.springframework.mvc.annotation.RequestBody;
import org.springframework.mvc.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired(value = "os")
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


    @RequestMapping("/list")
    public String list(HttpServletRequest httpServletRequest){

        httpServletRequest.setAttribute("name","zzq");
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
