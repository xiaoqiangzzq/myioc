package org.springframework.mvc.processer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 类说明
 *
 * @author Zeng zhiqiang
 * @version V1.0 创建时间: 2021/5/19 16:30
 * Copyright 2021 by WiteMedia
 */
public class ExecuteProcess {

    //存储所有的处理器
    private List<ProcesserChain> processerChainList;

    //请求
    private HttpServletRequest httpServletRequest;

    //响应
    private HttpServletResponse httpServletResponse;

    public ExecuteProcess(List<ProcesserChain> processerChainList, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.processerChainList = processerChainList;
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    public void execute(){
        if(null != processerChainList){
            for (ProcesserChain processerChain : processerChainList) {
                boolean process = processerChain.process(httpServletRequest, httpServletResponse);
                if(Boolean.FALSE.equals(process)){
                    break;
                }
            }
        }
    }

}
