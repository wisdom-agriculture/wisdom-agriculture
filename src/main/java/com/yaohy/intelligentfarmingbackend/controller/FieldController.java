package com.yaohy.intelligentfarmingbackend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.*;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.FieldParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.FieldPlantParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.FieldSensorRelationParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.OperationParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.*;
import com.yaohy.intelligentfarmingbackend.service.*;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.ml.CropRecommendTo;
import com.yaohy.intelligentfarmingbackend.util.ml.MlUtil;
import com.yaohy.intelligentfarmingbackend.util.request.ClassCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Api
@RestController
public class FieldController {

    @Autowired
    private FieldService fieldService;
    @Autowired
    private FieldSensorRelationService fieldSensorRelationService;
    @Autowired
    private SensorService sensorService;
    @Autowired
    private SensorTypeService sensorTypeService;
    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private OperationService operationService;
    @Autowired
    private FieldPlantRelationService fieldPlantRelationService;
    @Autowired
    private PlantService plantService;
    @Autowired
    private PlantStatusService plantStatusService;
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Resource
    private DeptService deptService;

    @App
    @Web
    @ApiOperation(value = "add")
    @RequestMapping(value = "/fields", method = RequestMethod.POST)
    public CommonResult<NoData> add(@RequestBody FieldParam param) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        Field field = new Field();
        field.setName(param.getName());
        field.setLatitude(param.getLatitude());
        field.setLongitude(param.getLongitude());
        field.setArea(param.getArea());
        field.setLocation(JSONUtil.toJsonStr(param.getLocation()));
        field.setDescription(param.getDescription());
        field.setUserId(userId);
        field.setStatusId(param.getStatusId());

        fieldService.save(field);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/fields", method = RequestMethod.PUT)
    public CommonResult update(@RequestBody FieldParam param) {
        Field field = new Field();
        field.setName(param.getName());
        field.setLatitude(param.getLatitude());
        field.setLongitude(param.getLongitude());
        field.setArea(param.getArea());
        field.setLocation(JSONUtil.toJsonStr(param.getLocation()));
        field.setDescription(param.getDescription());
        field.setStatusId(param.getStatusId());
        fieldService.updateById(field);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/fields", method = RequestMethod.DELETE)
    public CommonResult delete(@RequestParam("id") Integer id) {
        fieldService.removeById(id);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/fields", method = RequestMethod.GET)
    public CommonResult getAll() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        List<FieldResult> fieldResultList = fieldService.list().stream()
                .filter(field -> userId.equals(field.getUserId()) || userId.equals(field.getRenterId()))
                .map(field -> {
                    FieldResult fieldResult = new FieldResult();
                    fieldResult.setId(field.getId());
                    fieldResult.setName(field.getName());
                    fieldResult.setUserId(field.getUserId());
                    fieldResult.setLatitude(field.getLatitude());
                    fieldResult.setLongitude(field.getLongitude());
                    fieldResult.setArea(field.getArea());
                    fieldResult.setLocation(JSONUtil.parse(field.getLocation()));
                    fieldResult.setDescription(field.getDescription());
                    fieldResult.setRenterId(field.getRenterId());
                    fieldResult.setStatusId(field.getStatusId());

                    FieldPlantRelation isCrops = fieldPlantRelationService.getOne(new QueryWrapper<FieldPlantRelation>().eq("field_id", field.getId()).eq("status", 0));
                    if(isCrops != null) {
                        fieldResult.setIsCropsStatus(1);
                    }
                    else {
                        fieldResult.setIsCropsStatus(0);
                    }
                    return fieldResult;
                })
                .collect(Collectors.toList());

        FieldListResult fieldListResult = new FieldListResult();
        fieldListResult.setFieldList(fieldResultList);

        return CommonResult.success(fieldListResult);
    }


    @App
    @Web
    @ApiOperation(value = "addSensor")
    @RequestMapping(value = "/fields/sensors", method = RequestMethod.POST)
    public CommonResult<NoData> addSensor(@RequestBody FieldSensorRelationParam param) {
        FieldSensorRelation fieldSensorRelation = new FieldSensorRelation();
        BeanUtil.copyProperties(param, fieldSensorRelation);

        if (Objects.nonNull(fieldSensorRelationService.getOne(new QueryWrapper<FieldSensorRelation>().eq("sensor_id",param.getSensorId()))))
            return CommonResult.fail("请勿重复绑定一个传感器");

        fieldSensorRelationService.save(fieldSensorRelation);

        return CommonResult.success();
    }

    @App
    @Web
    @ApiOperation(value = "removeSensor")
    @RequestMapping(value = "/fields/sensors", method = RequestMethod.DELETE)
    public CommonResult<NoData> addSensor(@RequestParam("fieldId") Integer fieldId,@RequestParam("sensorId") Integer sensorId) {
        fieldSensorRelationService.remove(new QueryWrapper<FieldSensorRelation>().eq("field_id",fieldId).eq("sensor_id",sensorId));

        return CommonResult.success();
    }

    @App
    @Web
    @ApiOperation(value = "getSensor")
    @RequestMapping(value = "/fields/sensors", method = RequestMethod.GET)
    public CommonResult getSensor(@RequestParam("fieldId") Integer fieldId) {
        Integer[] ids = fieldSensorRelationService.list(new QueryWrapper<FieldSensorRelation>().eq("field_id", fieldId))
                .stream().map(FieldSensorRelation::getSensorId).toArray(Integer[]::new);
        if (ids.length==0) return CommonResult.fail("该地块未绑定传感器");
        Map<Integer, String> sensorTypeMap = sensorTypeService.list().stream().collect(Collectors.toMap(SensorType::getId, SensorType::getDescription));
        List<SensorResult> sensorResultList = sensorService.list(new QueryWrapper<Sensor>().in("id", ids))
                .stream().map(sensor -> {
                    SensorResult sensorResult = new SensorResult();
                    sensorResult.setId(sensor.getId());
                    sensorResult.setType(sensorTypeMap.get(sensor.getTypeId()));
                    sensorResult.setObjectId(sensor.getObjectId());

                    return sensorResult;
                }).collect(Collectors.toList());
        SensorListResult sensorListResult = new SensorListResult();
        sensorListResult.setSensorResultList(sensorResultList);

        return CommonResult.success(sensorListResult);
    }

//    @App
//    @Web
//    @ApiOperation(value = "setType")
//    @RequestMapping(value = "/fields/types", method = RequestMethod.PUT)
//    public CommonResult setType(@RequestBody FieldParam param) {
//
//        return CommonResult.success();
//    }
//
//    @App
//    @Web
//    @ApiOperation(value = "setType")
//    @RequestMapping(value = "/fields/types", method = RequestMethod.DELETE)
//    public CommonResult setType(@RequestParam Integer id) {
//
//
//        return CommonResult.success();
//    }

    @App
    @Web
    @ApiOperation(value = "operate")
    @RequestMapping(value = "/fields/operations", method = RequestMethod.POST)
    public CommonResult setType(@RequestBody OperationParam param) {
        Operation operation = new Operation();
        BeanUtil.copyProperties(param,operation);

        operationService.save(operation);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/fields/operations", method = RequestMethod.GET)
    public CommonResult getOperationRecord(@RequestParam(value = "fieldId",required = false) Integer fieldId,
                                           @RequestParam("current") Integer current,
                                           @RequestParam("size") Integer size) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));
        Page<Operation> operationPage;
        if (Objects.isNull(fieldId)){

            Integer[] fieldIds = fieldService.list(new QueryWrapper<Field>().eq("user_id", userId)).stream()
                    .map(Field::getId).toArray(Integer[]::new);
            operationPage = operationService.page(new Page<Operation>(current, size),
                    new QueryWrapper<Operation>().in("field_id", fieldIds).orderByDesc("time"));
        } else {
            operationPage = operationService.page(new Page<Operation>(current, size),
                    new QueryWrapper<Operation>().eq("field_id", fieldId).orderByDesc("time"));
        }

        Map<Integer, String> fieldNameMap = fieldService.list().stream()
                .collect(Collectors.toMap(Field::getId, Field::getName));

        List<Operation> operationList = operationPage.getRecords();
        List<OperationResult> operationResultList = operationList.stream().map(
                operation -> {
                    OperationResult operationResult = new OperationResult();
                    BeanUtil.copyProperties(operation, operationResult);
                    operationResult.setName(fieldNameMap.get(operationResult.getFieldId()));
                    return operationResult;
                }
        ).collect(Collectors.toList());

        OperationListResult operationListResult = new OperationListResult();
        operationListResult.setOperationResultList(operationResultList);

        return CommonResult.success(operationListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/fields/plants", method = RequestMethod.POST)
    public CommonResult add(@RequestBody FieldPlantParam param) {

        FieldPlantRelation fieldPlantRelation = new FieldPlantRelation();
        fieldPlantRelation.setFieldId(param.getFieldId());
        fieldPlantRelation.setPlantId(param.getPlantId());
        fieldPlantRelation.setPlantStatusId(param.getPlantStatusId());

        fieldPlantRelationService.save(fieldPlantRelation);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/fields/plants", method = RequestMethod.GET)
    public CommonResult get(@RequestParam("ids") Integer[] ids) {

        List<Plant> plantList = plantService.list();
        List<PlantStatus> plantStatusList = plantStatusService.list();

        Map<Integer, String> plantMap = plantList.stream().collect(Collectors.toMap(Plant::getId, Plant::getName));
        Map<Integer, String> plantStatusMap = plantStatusList.stream().collect(Collectors.toMap(PlantStatus::getId, PlantStatus::getName));

        List<FieldPlantRelation> fieldPlantRelationList = fieldPlantRelationService.list();
        FieldPlantListResult fieldPlantListResult = new FieldPlantListResult();
        fieldPlantListResult.setFieldList(Arrays.stream(ids).map(id -> {
            FieldPlantResult fieldPlantResult = new FieldPlantResult();
            fieldPlantResult.setId(id);
            fieldPlantResult.setPlantList(fieldPlantRelationList.stream().filter(
                    fieldPlantRelation -> Objects.equals(fieldPlantRelation.getFieldId(), id)
            ).map(
                    fieldPlantRelation -> {
                        PlantResult plantResult = new PlantResult();
                        plantResult.setId(fieldPlantRelation.getPlantId());
                        plantResult.setName(plantMap.get(fieldPlantRelation.getPlantId()));
                        plantResult.setStatus(plantStatusMap.get(fieldPlantRelation.getPlantStatusId()));

                        return plantResult;
                    }
            ).collect(Collectors.toList()));

            return fieldPlantResult;
        }).collect(Collectors.toList()));

        return CommonResult.success(fieldPlantListResult);
    }

    @App
    @Web
    @RequestMapping(value = "/fields/crop_pred",method = RequestMethod.GET)
    public CommonResult cropPred(@RequestParam(value = "id",required = false) Integer id,
                                 @RequestParam(value = "N",required = false) String N,
                                 @RequestParam(value = "P",required = false) String P,
                                 @RequestParam(value = "K",required = false) String K,
                                 @RequestParam(value = "humidity",required = false) String humidity,
                                 @RequestParam(value = "temperature",required = false) String temperature,
                                 @RequestParam(value = "ph",required = false) String ph){

        CropRecommendTo cropRecommendTo;
        if (Objects.nonNull(id)){
            cropRecommendTo = new CropRecommendTo();
            ArrayList<String> fakePlantList = new ArrayList<>();
            fakePlantList.add("荔枝");
            fakePlantList.add("玉米");
            fakePlantList.add("鹰嘴豆");
            fakePlantList.add("菜豆");
            fakePlantList.add("木豆");
            fakePlantList.add("扁豆");
            fakePlantList.add("绿豆");
            fakePlantList.add("黑豆");
            fakePlantList.add("小扁豆");
            fakePlantList.add("石榴");
            fakePlantList.add("香蕉");
            fakePlantList.add("芒果");
            fakePlantList.add("葡萄");
            fakePlantList.add("西瓜");
            fakePlantList.add("香瓜");
            fakePlantList.add("苹果");
            fakePlantList.add("橙子");
            fakePlantList.add("木瓜");
            fakePlantList.add("椰子");
            fakePlantList.add("咖啡豆");
            Collections.shuffle(fakePlantList);
            cropRecommendTo.setStatus("OK");
            cropRecommendTo.setLabel(fakePlantList.get(0));

//            //find latest sensor data
//            List<FieldSensorRelation> fieldSensorRelationList = fieldSensorRelationService.list(new QueryWrapper<FieldSensorRelation>().eq("field_id", id));
//
//            if (fieldSensorRelationList.isEmpty()) return CommonResult.fail("请先绑定传感器");
//            //TODO one field with only one sensor
//            FieldSensorRelation fieldSensorRelation = fieldSensorRelationList.get(0);
//            Integer sensorId = fieldSensorRelation.getSensorId();
//            SensorData sensorData = sensorDataService.getOne(new QueryWrapper<SensorData>().eq("sensor_id", sensorId)
//                    .orderByDesc("time").last("limit 1"));
//            if (Objects.isNull(sensorData)) return CommonResult.fail("传感器暂无数据");
//            JSONObject param = JSONUtil.parseObj(JSONUtil.parseObj(sensorData.getContent()).get("param"));
//
//            cropRecommendTo = MlUtil.cropRecommend(
//                    Objects.isNull(param.get("N"))? null:String.valueOf(param.get("N")),
//                    Objects.isNull(param.get("P"))? null:String.valueOf(param.get("P")),
//                    Objects.isNull(param.get("K"))? null:String.valueOf(param.get("k")),
//                    Objects.isNull(param.get("soilHumidity"))? null:String.valueOf(param.get("humidity")),
//                    Objects.isNull(param.get("temperature"))? null:String.valueOf(param.get("temperature")),
//                    Objects.isNull(param.get("ph"))? null:String.valueOf(param.get("ph")),
//                    Objects.isNull(param.get("rainfall"))? null:String.valueOf(param.get("rainfall"))
//            );
        } else {
            cropRecommendTo = MlUtil.cropRecommend(
                    Objects.isNull(N)? null:N,
                    Objects.isNull(P)? null:P,
                    Objects.isNull(K)? null:K,
                    Objects.isNull(humidity)? null:humidity,
                    Objects.isNull(temperature)? null:temperature,
                    Objects.isNull(ph)? null:ph,
//                    Objects.isNull(rainfall)? null:String.valueOf(rainfall)
                    null
            );
        }


        if (Objects.equals(cropRecommendTo.getStatus(), "OK")){
            return CommonResult.success(cropRecommendTo.getLabel());
        } else {
            return CommonResult.fail(cropRecommendTo.getMessage());
        }

    }

    @App
    @Web
    @RequestMapping(value = "/fields/disease_pred",method = RequestMethod.GET)
    public CommonResult diseasePred(){


        DiseaseResult diseaseResult = new DiseaseResult();
        diseaseResult.setId(1);
        diseaseResult.setDisease("斑点病");
        diseaseResult.setFieldName("七号地块");
        diseaseResult.setPlant("苹果");
        diseaseResult.setTime(1664357442000L);
        diseaseResult.setStatus(0);
        diseaseResult.setImg("https://image.yaohy.cn/intelligent-farming/disease/1.jpeg");

        DiseaseResult diseaseResult1 = new DiseaseResult();
        diseaseResult1.setId(2);
        diseaseResult1.setDisease("锈病");
        diseaseResult1.setFieldName("四号地块");
        diseaseResult1.setPlant("苹果");
        diseaseResult1.setTime(1663670195000L);
        diseaseResult1.setStatus(0);
        diseaseResult1.setImg("https://image.yaohy.cn/intelligent-farming/disease/2.jpeg");


        DiseaseResult diseaseResult2 = new DiseaseResult();
        diseaseResult2.setId(3);
        diseaseResult2.setDisease("锈病");
        diseaseResult2.setFieldName("五号地块");
        diseaseResult2.setPlant("苹果");
        diseaseResult2.setTime(1658308579000L);
        diseaseResult2.setStatus(1);
        diseaseResult2.setImg("https://image.yaohy.cn/intelligent-farming/disease/3.jpeg");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        List<DiseaseResult> diseaseResultList = new ArrayList<>();
        diseaseResultList.add(diseaseResult);
        diseaseResultList.add(diseaseResult1);
        diseaseResultList.add(diseaseResult2);

        Collections.shuffle(diseaseResultList);

        DiseaseListResult diseaseListResult = new DiseaseListResult();
        diseaseListResult.setDiseaseList(diseaseResultList);

        return CommonResult.success(diseaseListResult);
    }

    @Web
    @App
    @RequestMapping(value = "/fields/information", method = RequestMethod.GET)
    public CommonResult getFieldInformation(@RequestParam @NotNull Integer fieldId) {
        Field field = fieldService.getById(fieldId);

        if(field == null) {
            return CommonResult.fail("地块异常");
        }

        FieldInformationResult fieldInformationResult = new FieldInformationResult();
        ClassCopyUtils.ClassCopy(fieldInformationResult, field);

        Integer userId = field.getUserId();
        User user = userService.getById(userId);

        if(user == null) {
            return CommonResult.fail("地块账户异常");
        }

        Integer deptId = user.getDeptId();

        Dept dept = deptService.getById(deptId);
        if(dept == null) {
            return  CommonResult.fail("地块归属公司异常");
        }
        fieldInformationResult.setDeptName(dept.getDeptName());

        FieldPlantRelation currentCrops = fieldPlantRelationService.getOne(new QueryWrapper<FieldPlantRelation>().eq("field_id", fieldId).eq("status", 0));

        if(currentCrops != null) {
            long cropsId = currentCrops.getCropsId();
            Date time = currentCrops.getTime();
            fieldInformationResult.setTime(time);
            Integer plantId = currentCrops.getPlantId();
            fieldInformationResult.setCropsId(cropsId);
            Plant plant = plantService.getOne(new QueryWrapper<Plant>().eq("id", plantId));
            if(plant != null) {
                fieldInformationResult.setCropsName(plant.getName());
                fieldInformationResult.setImage(plant.getImgUrl());
            }

        }
        return CommonResult.success(fieldInformationResult);
    }

}
