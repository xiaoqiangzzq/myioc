package com.yj.test;

import org.springframework.container.ClassPathXmlApplicationContext;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/10 15:32
 * Copyright 2021 by WiteMedia
 */
public class TestSpringIoc {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
