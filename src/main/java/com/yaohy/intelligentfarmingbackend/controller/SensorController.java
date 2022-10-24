package com.yaohy.intelligentfarmingbackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Field;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Sensor;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.SensorParam;
import com.yaohy.intelligentfarmingbackend.service.SensorService;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Api
@RestController
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private HttpServletRequest request;

    @App
    @Web
    @ApiOperation(value = "add")
    @RequestMapping(value = "/sensors",method = RequestMethod.POST)
    public CommonResult add(@RequestBody SensorParam param){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        Sensor sensor = new Sensor();
        BeanUtil.copyProperties(param,sensor);

        if (Objects.nonNull(sensorService.getOne(new QueryWrapper<Sensor>().eq("object_id",param.getObjectId()))))
            return CommonResult.fail("传感器已存在");

        sensor.setUserId(userId);
        sensorService.save(sensor);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/sensors",method = RequestMethod.GET)
    public CommonResult getAll(){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<Sensor> sensorList = sensorService.list(new QueryWrapper<Sensor>().eq("user_id",userId));

        return CommonResult.success(sensorList);
    }

}
