package com.yaohy.intelligentfarmingbackend.config.authority.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface App {

    String value() default "";

    String path() default "";

}
