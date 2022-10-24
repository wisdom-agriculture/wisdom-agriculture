package com.yaohy.intelligentfarmingbackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Sensor;
import com.yaohy.intelligentfarmingbackend.pojo.domain.SensorType;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.SensorTypeParam;
import com.yaohy.intelligentfarmingbackend.service.SensorTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping("/sensor")
public class SensorTypeController {

    @Autowired
    private SensorTypeService sensorTypeService;

    @App
    @Web
    @ApiOperation(value = "add")
    @RequestMapping(value = "/types",method = RequestMethod.POST)
    public CommonResult add(@RequestBody SensorTypeParam param){
        SensorType sensorType = new SensorType();
        BeanUtil.copyProperties(param,sensorType);
        sensorTypeService.save(sensorType);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/types",method = RequestMethod.GET)
    public CommonResult getAll(){
        //TODO 按照用户id搜索土地 目前这里会返回所有土地
        List<SensorType> sensorTypeList = sensorTypeService.list();

        return CommonResult.success(sensorTypeList);
    }

}
