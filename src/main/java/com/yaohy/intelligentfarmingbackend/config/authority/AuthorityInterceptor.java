package com.yaohy.intelligentfarmingbackend.config.authority;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

public class AuthorityInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        String requestURI = request.getRequestURI();
        //check if login request
        return requestURI.equals("/web/login")
                || requestURI.equals("/app/login")
                || requestURI.equals("/web/register")
                || requestURI.equals("/app/register")
                || requestURI.equals("/verification/codes")
                || requestURI.startsWith("/trace")
                || requestURI.startsWith("/error")
                || requestURI.startsWith(("/swagger-ui"))
                || requestURI.startsWith("/v2")
                || requestURI.startsWith("/swagger-resources");
//        return true;
    }
}
