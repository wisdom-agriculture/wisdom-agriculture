package com.yaohy.intelligentfarmingbackend.config.authority;

import com.yaohy.intelligentfarmingbackend.config.authority.decorator.EndAuthorityDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new EndAuthorityDecorator(new AuthorityInterceptor())).addPathPatterns(("/**")).order(1);
    }
}
