package com.yj.service;

import com.yj.bean.Order;

import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/8 15:05
 * Copyright 2021 by WiteMedia
 */
public interface OrderService {

    List<Order> getOrders();

    int addOrder(Order order);
}
