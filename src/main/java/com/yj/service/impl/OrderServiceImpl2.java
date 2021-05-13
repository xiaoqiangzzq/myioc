package com.yj.service.impl;

import com.yj.bean.Order;
import com.yj.service.OrderService;
import org.springframework.annotation.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/8 15:05
 * Copyright 2021 by WiteMedia
 */
@Service(value = "os")
public class OrderServiceImpl2 implements OrderService {
    public List<Order> getOrders() {

        List<Order> list = new ArrayList<Order>();

        list.add(new Order(5,"water order",666.2));
        list.add(new Order(6,"lv order",1245));

        return list;
    }

    @Override
    public int addOrder(Order order) {
        System.out.println("新增订单。。。");
        return 1;
    }
}
