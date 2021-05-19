package org.springframework.mvc.processer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/19 16:28
 * Copyright 2021 by WiteMedia
 */
public interface ProcesserChain {

    boolean process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
