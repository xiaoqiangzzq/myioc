package org.springframework.mvc.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandleAdapter {

    private HandlerMapping handlerMapping;

    private HttpServletRequest httpServletRequest;

    private HttpServletResponse httpServletResponse;

    public HandleAdapter(HandlerMapping handlerMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.handlerMapping = handlerMapping;
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    public void handle(){

    }

    public HandlerMapping getHandlerMapping() {
        return handlerMapping;
    }

    public void setHandlerMapping(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }
}
