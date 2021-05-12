package com.yj.dao.impl;

import com.yj.bean.Order;
import com.yj.dao.OrderDao;
import org.springframework.annotation.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/12 16:46
 * Copyright 2021 by WiteMedia
 */
@Repository
public class OrderDaoImpl implements OrderDao {
    @Override
    public List<Order> getOrders() {

        System.out.println("调用dao。。。");
        List<Order> list = new ArrayList<Order>();

        list.add(new Order(5,"water order",666));
        list.add(new Order(6,"lv order",1245));

        return list;
    }
}
