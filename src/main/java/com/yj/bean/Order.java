package com.yj.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/8 14:47
 * Copyright 2021 by WiteMedia
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int oid;
    private String title;
    private double money;

}
