package com.yj.dao;

import com.yj.bean.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/12 16:45
 * Copyright 2021 by WiteMedia
 */
public interface OrderDao {
    List<Order> getOrders();

    int excuteMoney(String name,double money) throws ClassNotFoundException, SQLException;
}
