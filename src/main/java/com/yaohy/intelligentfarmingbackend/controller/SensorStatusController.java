package com.yaohy.intelligentfarmingbackend.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.to.SensorStatusTo;
import com.yaohy.intelligentfarmingbackend.util.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("/sensors")
public class SensorStatusController {


    @Autowired
    private RedisUtil redisUtil;

    @App
    @Web
    @RequestMapping(value = "/statuses", method = RequestMethod.GET)
    public CommonResult get(@RequestParam("id") Integer id){

        SensorStatusTo sensorStatusTo = JSONUtil.toBean(redisUtil.get(id.toString() + "-event").toString(), SensorStatusTo.class);

        return CommonResult.success(sensorStatusTo);
    }

}
