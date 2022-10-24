package com.yaohy.intelligentfarmingbackend.controller;

import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.UserParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.TokenResult;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/app")
public class AppUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResult<TokenResult> Register(@RequestBody UserParam param){

        return userService.appRegister(param.getPhone(),param.getPassword(),param.getCode());
    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult<TokenResult> Login(@RequestBody UserParam param){

        if (Objects.nonNull(param.getCode())) return userService.appDirectLogin(param.getPhone(),param.getCode());
        return userService.appLogin(param.getPhone(),param.getPassword());
    }

}
