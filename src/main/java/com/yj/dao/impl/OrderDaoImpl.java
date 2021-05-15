package com.yj.dao.impl;

import com.yj.bean.Order;
import com.yj.dao.OrderDao;
import org.springframework.annotation.Repository;
import org.springframework.tx.TransactionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    @Override
    public int excuteMoney(String name,double money) throws ClassNotFoundException, SQLException {
//        Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
//        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/account?serverTimezone=UTC&characterEncoding=UTF-8","root","123456");
        Connection connection = TransactionManager.getThreadConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("update t_account set money= money + ? where name = ?");
        preparedStatement.setObject(1,money);
        preparedStatement.setObject(2,name);
        int i = preparedStatement.executeUpdate();
        //connection.close();
        return i;
    }

//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        new OrderDaoImpl().excuteMoney("666",3000);
//    }
}
