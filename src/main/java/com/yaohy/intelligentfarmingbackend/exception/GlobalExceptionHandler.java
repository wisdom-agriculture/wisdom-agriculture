package com.yaohy.intelligentfarmingbackend.exception;

import com.yaohy.intelligentfarmingbackend.config.lark.LarkRobot;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private LarkRobot larkRobot;

    @ExceptionHandler(Exception.class)
    public CommonResult exceptionHandler(Exception e){
        log.error(e.getMessage());
        larkRobot.alertException(e);

        return CommonResult.fail();
    }

    @ExceptionHandler(RestClientException.class)
    public CommonResult restClientExceptionHandler(RestClientException e) {
        log.error(e.getMessage());
        larkRobot.alertException(e);

        return CommonResult.fail("数据异常");
    }
}
