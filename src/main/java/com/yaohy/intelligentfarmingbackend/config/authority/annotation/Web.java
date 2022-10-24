package com.yaohy.intelligentfarmingbackend.config.authority.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Web {

    String value() default "";

    String path() default "";

}
