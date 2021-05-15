package com.yj.service.impl;

import com.yj.bean.Order;
import com.yj.dao.OrderDao;
import com.yj.service.OrderService;
import org.springframework.annotation.Autowired;
import org.springframework.annotation.Service;
import org.springframework.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/8 15:05
 * Copyright 2021 by WiteMedia
 */
@Service(value = "os")
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

    @Override
    @Transactional
    public int transferMoney(String fromName, String toName, double money) {
        try {
            int i = orderDao.excuteMoney(fromName, money * (-1));
            System.out.println(fromName + "转账出去" + money);
            System.out.println("===================================");
            int k = 100/0;
            int j = orderDao.excuteMoney(toName, money );
            System.out.println(toName + "转账收到" + money);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  1;
    }
}
