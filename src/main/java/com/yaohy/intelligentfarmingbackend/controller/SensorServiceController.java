package com.yaohy.intelligentfarmingbackend.controller;

import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/sensors/services")
public class SensorServiceController {

    @App
    @Web
    @RequestMapping(value = "/start",method = RequestMethod.GET)
    public CommonResult start(@RequestParam("id") Integer id){

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/stop",method = RequestMethod.GET)
    public CommonResult stop(@RequestParam("id") Integer id){

        return CommonResult.success();
    }

}
