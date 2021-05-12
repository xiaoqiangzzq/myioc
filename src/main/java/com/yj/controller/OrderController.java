package com.yj.controller;

import com.yj.bean.Order;
import com.yj.service.Impl.OrderServiceImpl;
import com.yj.service.OrderService;
import org.springframework.annotation.Autowired;
import org.springframework.annotation.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/8 15:08
 * Copyright 2021 by WiteMedia
 */
@Controller
public class OrderController {

    @Autowired(value = "os1")
    OrderService orderService;

    public String  getOrders(){
        System.out.println("调用Controller。。。。");
        List<Order> orders = orderService.getOrders();
        for (Order order : orders) {
            System.out.println(order.getTitle());
        }
        return "调用成功";
    }
}
