package com.yaohy.intelligentfarmingbackend.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.FieldSensorRelation;
import com.yaohy.intelligentfarmingbackend.pojo.domain.SensorData;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.SensorDataListResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.SensorDataResult;
import com.yaohy.intelligentfarmingbackend.service.FieldSensorRelationService;
import com.yaohy.intelligentfarmingbackend.service.SensorDataService;
import com.yaohy.intelligentfarmingbackend.util.RedisUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api
@RestController
@RequestMapping("/sensors")
public class SensorDataController {

    @Autowired
    private FieldSensorRelationService fieldSensorRelationService;
    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private RedisUtil redisUtil;

    @App
    @Web
    @RequestMapping(value = "/datas", method = RequestMethod.GET)
    public CommonResult get(@RequestParam("fieldId") Integer fieldId,
                            @RequestParam("current") Integer current,
                            @RequestParam("size") Integer size) {
        Integer[] ids = fieldSensorRelationService.list(new QueryWrapper<FieldSensorRelation>()
                        .eq("field_id", fieldId))
                .stream().map(FieldSensorRelation::getSensorId).toArray(Integer[]::new);
//        List<SensorData> sensorDataList = sensorDataService.list(new QueryWrapper<SensorData>().in("sensor_id", ids));
        Page<SensorData> sensorDataPage = sensorDataService.page(new Page<SensorData>(current,size),
                new QueryWrapper<SensorData>().in("sensor_id", ids));
        List<SensorData> sensorDataList = sensorDataPage.getRecords();

        SensorDataListResult sensorDataListResult = new SensorDataListResult();

        ArrayList<SensorDataListResult.SensorList> sensorLists = new ArrayList<>();
        for (Integer id : ids) {
            SensorDataListResult.SensorList sensorList = new SensorDataListResult.SensorList();
            sensorList.setId(id);
            sensorList.setSensorDataList(sensorDataList.stream().filter((sensorData) -> {
                return Objects.equals(sensorData.getSensorId(), id);
            }).map(sensorData -> {
                SensorDataResult sensorDataResult = new SensorDataResult();
                sensorDataResult.setId(sensorData.getId());
                sensorDataResult.setSensorId(sensorData.getSensorId());
                sensorDataResult.setContent(JSONUtil.parse(sensorData.getContent()));
                sensorDataResult.setTime(sensorData.getTime());

                return sensorDataResult;
            }).collect(Collectors.toList()));

            sensorLists.add(sensorList);
        }

        sensorDataListResult.setSensorList(sensorLists);

        return CommonResult.success(sensorDataListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/datas/latest", method = RequestMethod.GET)
    public CommonResult getLatest(@RequestParam("fieldId") Integer fieldId) {
        Integer[] ids = fieldSensorRelationService.list(new QueryWrapper<FieldSensorRelation>()
                        .eq("field_id", fieldId))
                .stream().map(FieldSensorRelation::getSensorId).toArray(Integer[]::new);
        SensorDataListResult sensorDataListResult = new SensorDataListResult();
        ArrayList<SensorDataListResult.SensorList> sensorLists = new ArrayList<>();
        for (Integer id : ids) {
            SensorDataListResult.SensorList sensorList = new SensorDataListResult.SensorList();
            sensorList.setId(id);
            ArrayList<Object> sensorDataList = new ArrayList<>();
            sensorDataList.add(JSONUtil.parse(redisUtil.get(id.toString()+"-property")));
            sensorList.setSensorDataList(sensorDataList);

            sensorLists.add(sensorList);
        }

        sensorDataListResult.setSensorList(sensorLists);

        return CommonResult.success(sensorDataListResult);
    }

}
