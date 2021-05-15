package org.springframework.tx;

import java.sql.Connection;
import java.sql.DriverManager;

//事务管理器
public class TransactionManager {

    static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        threadLocal.set(getConnection());
    }

    public static Connection getThreadConnection(){
        return threadLocal.get();
    }

    public static Connection getConnection(){
        try {
            Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/account?serverTimezone=UTC&characterEncoding=UTF-8","root","123456");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
