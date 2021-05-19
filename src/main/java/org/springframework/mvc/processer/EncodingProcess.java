package org.springframework.mvc.processer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/19 17:12
 * Copyright 2021 by WiteMedia
 */
public class EncodingProcess implements ProcesserChain {


    @Override
    public boolean process(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try {
            httpServletRequest.setCharacterEncoding("UTF-8");
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
