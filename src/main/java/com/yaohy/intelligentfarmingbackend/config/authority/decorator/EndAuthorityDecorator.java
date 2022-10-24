package com.yaohy.intelligentfarmingbackend.config.authority.decorator;

import com.yaohy.intelligentfarmingbackend.constant.RoleConstant;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class EndAuthorityDecorator extends AuthorityDecorator {

    private static Set<String> webSourceSet;

    private static Set<String> appSourceSet;

    public EndAuthorityDecorator(HandlerInterceptor handlerInterceptor) {
        super(handlerInterceptor);
    }

    public static Set<String> getWebSourceSet() {
        return webSourceSet;
    }

    public static void setWebSourceSet(Set<String> webSourceSet) {
        EndAuthorityDecorator.webSourceSet = webSourceSet;
    }

    public static Set<String> getAppSourceSet() {
        return appSourceSet;
    }

    public static void setAppSourceSet(Set<String> appSourceSet) {
        EndAuthorityDecorator.appSourceSet = appSourceSet;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //检查是否是登陆请求 如果是则直接返回
        if (super.preHandle(request, response, handler)) return true;

        //不是登陆请求 开始进行匹配
        String requestURI = request.getRequestURI();
        String token = request.getHeader("Authorization");
        if (Objects.nonNull(token)) {
            String end = JwtUtil.decodeEnd(token);
            String webUserCode = RoleConstant.WEB_USER.getCode();
            String appUserCode = RoleConstant.APP_USER.getCode();

            if (Objects.nonNull(end)) {
                //检查是否是Web端用户
                if (end.equals(webUserCode)) {
                    //匹配uri
                    if (webSourceSet.contains(requestURI)) {

                        AuthorityDecorator.setFlag(true);
                        log.info("user from web checked");

                        return true;
                    }
                }
                //检查是否是App端用户
                else if (end.equals(appUserCode))
                    //匹配uri
                    if (appSourceSet.contains(requestURI)) {

                        AuthorityDecorator.setFlag(true);
                        log.info("user from app checked");

                        return true;
                    }
            }
        }

        response.sendRedirect(request.getContextPath() + "/error/permission/denied");
        return false;
    }
}

