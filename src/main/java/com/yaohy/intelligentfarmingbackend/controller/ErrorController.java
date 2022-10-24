package com.yaohy.intelligentfarmingbackend.controller;

import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/permission/denied")
    public CommonResult permissionDenied(){

        return CommonResult.fail("Permission Denied");
    }

}
