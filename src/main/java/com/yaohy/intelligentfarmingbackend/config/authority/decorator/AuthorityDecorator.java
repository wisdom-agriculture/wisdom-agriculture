package com.yaohy.intelligentfarmingbackend.config.authority.decorator;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AuthorityDecorator implements HandlerInterceptor {

    private static boolean flag = false;

    public static synchronized boolean getFlag() {
        return flag;
    }

    public static synchronized void setFlag(boolean flag) {
        AuthorityDecorator.flag = flag;
    }

    public HandlerInterceptor getHandlerInterceptor() {
        return handlerInterceptor;
    }

    public void setHandlerInterceptor(HandlerInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
    }

    private HandlerInterceptor handlerInterceptor;

    private AuthorityDecorator(){}

    public AuthorityDecorator(HandlerInterceptor handlerInterceptor){
        this.handlerInterceptor = handlerInterceptor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handlerInterceptor.preHandle(request, response, handler);
    }
}
