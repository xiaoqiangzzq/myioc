package com.yj.service.impl;

import com.yj.bean.Order;
import com.yj.dao.OrderDao;
import com.yj.service.OrderService;
import org.springframework.annotation.Autowired;
import org.springframework.annotation.Service;

import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/8 15:05
 * Copyright 2021 by WiteMedia
 */
@Service(value = "os1")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;
    public List<Order> getOrders() {
        System.out.println("调用service。。。");
        return orderDao.getOrders();
    }

    @Override
    public int addOrder(Order order) {
        return 0;
    }
}
