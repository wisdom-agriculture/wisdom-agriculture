package com.yaohy.intelligentfarmingbackend.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.constant.SensorStatusConstant;
import com.yaohy.intelligentfarmingbackend.pojo.domain.*;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.panel.*;
import com.yaohy.intelligentfarmingbackend.pojo.to.SensorStatusTo;
import com.yaohy.intelligentfarmingbackend.service.*;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.RedisUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/panel")
@RestController
public class DataPanelController {

    @Resource
    private HttpServletRequest request;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FieldService fieldService;
    @Autowired
    private FieldPlantRelationService fieldPlantRelationService;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorTypeService sensorTypeService;
    @Autowired
    private FieldSensorRelationService fieldSensorRelationService;
    @Autowired
    private DiseaseService diseaseService;

    @App
    @Web
    @RequestMapping(value = "/fieldPlantArea", method = RequestMethod.GET)
    public CommonResult fieldPlantArea() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        Map<Integer, String> fieldIdNameMap = fieldService.list(new QueryWrapper<Field>().eq("user_id", userId)
        ).stream().collect(Collectors.toMap(Field::getId, Field::getName));
        List<FieldPlantRelation> fieldPlantRelationList = fieldPlantRelationService.list(new QueryWrapper<FieldPlantRelation>().in("field_id", fieldIdNameMap.keySet()).orderByDesc("area"));

        ArrayList<FieldPlantAreaResult> list = new ArrayList<>();
        for (FieldPlantRelation fieldPlantRelation : fieldPlantRelationList) {
            FieldPlantAreaResult fieldPlantAreaResult = new FieldPlantAreaResult();
            fieldPlantAreaResult.setName(fieldIdNameMap.get(fieldPlantRelation.getFieldId()));
            fieldPlantAreaResult.setPlant(fieldPlantRelation.getCropsName());
            fieldPlantAreaResult.setArea(fieldPlantRelation.getArea());

            list.add(fieldPlantAreaResult);
        }

        return CommonResult.success(list);
    }

    @App
    @Web
    @RequestMapping(value = "/plantArea", method = RequestMethod.GET)
    public CommonResult plantArea() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        Map<Integer, String> fieldIdNameMap = fieldService.list(new QueryWrapper<Field>().eq("user_id", userId)
        ).stream().collect(Collectors.toMap(Field::getId, Field::getName));
        List<FieldPlantRelation> fieldPlantRelationList = fieldPlantRelationService.list(new QueryWrapper<FieldPlantRelation>().in("field_id", fieldIdNameMap.keySet()).orderByDesc("area"));

        ArrayList<PlantAreaResult> list = new ArrayList<>();
        for (FieldPlantRelation fieldPlantRelation : fieldPlantRelationList) {
            PlantAreaResult plantAreaResult = new PlantAreaResult();
            plantAreaResult.setPlant(fieldPlantRelation.getCropsName());
            plantAreaResult.setArea(fieldPlantRelation.getArea());

            list.add(plantAreaResult);
        }

        return CommonResult.success(list);
    }

    @App
    @Web
    @RequestMapping(value = "/sensor", method = RequestMethod.GET)
    public CommonResult sensor() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<Sensor> sensorList = sensorService.list(new QueryWrapper<Sensor>().eq("user_id", userId));
        Map<Integer, String> sensorTypeMap = sensorTypeService.list().stream()
                .collect(Collectors.toMap(SensorType::getId, SensorType::getDescription));
        Map<Integer, Integer> fieldSensorRelationMap = fieldSensorRelationService.list()
                .stream().collect(Collectors.toMap(FieldSensorRelation::getSensorId, FieldSensorRelation::getFieldId));
        Map<Integer, String> fieldIdNameMap = fieldService.list(new QueryWrapper<Field>().eq("user_id", userId)
        ).stream().collect(Collectors.toMap(Field::getId, Field::getName));

        ArrayList<SensorResult> sensorResultList = new ArrayList<>();
        for (Sensor sensor : sensorList) {
            Object o = redisUtil.get(sensor.getId() + "-event");
            SensorStatusTo sensorStatusTo = JSONUtil.toBean(JSONUtil.parseObj(o), SensorStatusTo.class);

            SensorResult sensorResult = new SensorResult();
            sensorResult.setName(sensorTypeMap.get(sensor.getTypeId()));
            sensorResult.setStatus(
                    Objects.nonNull(sensorStatusTo) ?
                            Objects.equals(
                                    sensorStatusTo.getStatus(),
                                    SensorStatusConstant.DEVICE_ONLINE.getStatus()
                            ) ? 1 : 0 : 0);
            sensorResult.setField(fieldIdNameMap.get(fieldSensorRelationMap.get(sensor.getId())));

            sensorResultList.add(sensorResult);
        }

        return CommonResult.success(sensorResultList);
    }

    @App
    @Web
    @RequestMapping(value = "/diseaseAlarm", method = RequestMethod.GET)
    public CommonResult diseaseAlarm() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<Integer> fieldIdList = fieldService.list(new QueryWrapper<Field>().eq("user_id", userId))
                .stream().map(Field::getId).collect(Collectors.toList());

        List<String> diseaseList = diseaseService.list(new QueryWrapper<Disease>().in("field_id", fieldIdList))
                .stream().map(Disease::getDisease).collect(Collectors.toList());
        HashSet<String> diseaseSet = new HashSet<>(diseaseList);

        ArrayList<DiseaseAlarmResult> diseaseAlarmResultList = new ArrayList<>();
        int size = diseaseList.size();
        for (String disease : diseaseSet) {
            DiseaseAlarmResult diseaseAlarmResult = new DiseaseAlarmResult();
            diseaseAlarmResult.setName(disease);
            diseaseAlarmResult.setPercentage(diseaseList.stream().filter(dis -> {
                return Objects.equals(dis, disease);
            }).count() * 1.0 / size);
            diseaseAlarmResultList.add(diseaseAlarmResult);
        }

        return CommonResult.success(diseaseAlarmResultList);
    }

    @App
    @Web
    @RequestMapping(value = "/diseaseTime", method = RequestMethod.GET)
    public CommonResult diseaseTime() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        List<Integer> fieldIdList = fieldService.list(new QueryWrapper<Field>().eq("user_id", userId))
                .stream().map(Field::getId).collect(Collectors.toList());

        List<Disease> diseaseList = diseaseService.list(new QueryWrapper<Disease>().in("field_id", fieldIdList));
        HashSet<String> diseaseSet = new HashSet<>(
                diseaseService.list(new QueryWrapper<Disease>().in("field_id", fieldIdList))
                        .stream().map(Disease::getDisease).collect(Collectors.toList()));

        ArrayList<DiseaseTimeResult> diseaseTimeResultList = new ArrayList<>();
        for (String dis : diseaseSet) {

            Integer[] time = new Integer[12];
            for (int i = 0; i < 12; i++) {
                int cnt = 0;
                Calendar calendar = Calendar.getInstance();
                for (Disease disease : diseaseList) {
                    if (!Objects.equals(disease.getDisease(), dis)) continue;
                    calendar.setTime(new Date(disease.getTime()));
                    if (calendar.get(Calendar.MONTH) == i) cnt++;
                }
                time[i] = cnt;
            }

            DiseaseTimeResult diseaseTimeResult = new DiseaseTimeResult();
            diseaseTimeResult.setName(dis);
            diseaseTimeResult.setTime(time);

            diseaseTimeResultList.add(diseaseTimeResult);
        }

        return CommonResult.success(diseaseTimeResultList);
    }

}
