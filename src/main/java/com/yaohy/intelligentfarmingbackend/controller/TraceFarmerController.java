package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.constant.BlockChainConstant;
import com.yaohy.intelligentfarmingbackend.mapper.*;
import com.yaohy.intelligentfarmingbackend.pojo.domain.*;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.FieldPlantParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.chainParam.RecordCropsGrowParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.CropsListResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.NoData;
import com.yaohy.intelligentfarmingbackend.service.FieldPlantRelationService;
import com.yaohy.intelligentfarmingbackend.service.PlantService;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.SnowFlake;
import com.yaohy.intelligentfarmingbackend.util.TimeUtil;
import com.yaohy.intelligentfarmingbackend.util.request.ClassCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Valentine
 */
@RequestMapping("/farmer")
@RestController
public class TraceFarmerController {
    @Resource
    HttpServletRequest request;
    @Resource
    RestTemplate restTemplate;
    @Resource
    UserService userService;
    @Resource
    DeptMapper deptMapper;
    @Resource
    PlantService plantService;
    @Resource
    FieldPlantRelationService fieldPlantRelationService;
    @Resource
    FieldPlantRelationMapper fieldPlantRelationMapper;

    @RequestMapping(value = "/recordCropsGrowOnChain", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult<NoData> recordCropsGrow(@RequestBody RecordCropsGrowParam param) {
        Long id = SnowFlake.nextId();
        List list = new ArrayList();

        list.add(String.valueOf(id));
        list.add(String.valueOf(id));
        list.add(String.valueOf(param.getCropsId()));

        list.add(TimeUtil.getNowTime());
        list.add(param.getUrl());
        list.add(param.getTemperature());
        list.add(param.getGrowthSituation());
        list.add(param.getMoisture());
        list.add(param.getLightConditions());
        list.add(param.getRemark());

        HashMap reqMap = new HashMap<String, Object[]>();
        System.out.println(list);
        reqMap.put("cropsGrowArray", list);

        System.out.println(reqMap);

        restTemplate.postForObject(BlockChainConstant.FARMER_API+"/recordCropsGrow", reqMap, String.class);

        return CommonResult.success();
    }


    @RequestMapping(value = "/factoryId", method = RequestMethod.GET)
    @App
    @Web
    public CommonResult getFactoryId() {
        List<Dept> list = deptMapper.selectList(new QueryWrapper<Dept>().eq("parent_id", 119));
        return CommonResult.success(list);
    }

    @RequestMapping(value = "/plants", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult add(@RequestBody FieldPlantParam param) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if(user == null) {
            return CommonResult.fail("账号异常");
        }

        List<FieldPlantRelation> fieldPlantRelations = fieldPlantRelationMapper.selectList(new QueryWrapper<FieldPlantRelation>().eq("field_id", param.getFieldId()).eq("status", 0));
        if(!fieldPlantRelations.isEmpty()) {
            return CommonResult.fail("当前土地已种植");
        }

        //将cropsId等信息存入数据库
        FieldPlantRelation fieldPlantRelation = new FieldPlantRelation();
        fieldPlantRelation.setFieldId(param.getFieldId());
        fieldPlantRelation.setPlantId(param.getPlantId());
        fieldPlantRelation.setPlantStatusId(param.getPlantStatusId());
        fieldPlantRelation.setArea(param.getArea());
        long cropsId = SnowFlake.nextId();
        fieldPlantRelation.setCropsId(cropsId);
        String plantName = plantService.getById(param.getPlantId()).getName();
        fieldPlantRelation.setCropsName(plantName);
        fieldPlantRelation.setUserId(userId);
        fieldPlantRelation.setTime(TimeUtil.getDate());

        //将cropsId上传到区块链
        List list = new ArrayList();

        //cropsId
        list.add(String.valueOf(cropsId));
        //cropsId
        list.add(String.valueOf(cropsId));
        //作物名称
        list.add(plantName);
        //作物地址
        list.add(param.getDetailedAddress());
        //农户
        list.add(user.getNickname());
        //肥料名称
        list.add(param.getFertilizerName());
        //种植方式
        list.add(param.getPlantingMethod());
        //是否套袋种植
        list.add(param.getBagging());
        //育苗周期
        list.add(param.getSeedingPeriod());
        //灌溉周期
        list.add(param.getIrrigationCycle());
        //施肥周期
        list.add(param.getFertilizerCycle());
        //除草周期
        list.add(param.getWeedingCycle());
        //备注
        list.add(param.getRemark());
        //登记时间
        list.add(TimeUtil.getNowTime());
        //种植年度
        list.add(param.getPlantingYear());
        //农户电话
        list.add(user.getPhone());

        HashMap reqMap = new HashMap<String, Object[]>();
        reqMap.put("cropsArray", list);
        System.out.println(reqMap);

        restTemplate.postForObject(BlockChainConstant.FARMER_API+"/createCrops", reqMap, String.class);

        fieldPlantRelationService.save(fieldPlantRelation);
        return CommonResult.success();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Web
    @App
    public CommonResult listPlants() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if(user == null) {
            return CommonResult.fail("账号异常");
        }

        List<FieldPlantRelation> list = fieldPlantRelationMapper.selectList(new QueryWrapper<FieldPlantRelation>().eq("user_id", userId));
        List<CropsListResult> results = new ArrayList<>();
        for (FieldPlantRelation fieldPlantRelation : list) {
            Integer userId1 = fieldPlantRelation.getUserId();
            User byId = userService.getById(userId1);
            CropsListResult cropsListResult = new CropsListResult();
            ClassCopyUtils.ClassCopy(cropsListResult, fieldPlantRelation);
            if(byId != null) {
                String nickname = byId.getNickname();
                cropsListResult.setUserName(nickname);
                Integer deptId = byId.getDeptId();
                if(deptId != null) {
                    Dept dept = deptMapper.selectById(deptId);
                    if(dept != null) {
                        cropsListResult.setDeptName(dept.getDeptName());
                    }
                }
            }
            results.add(cropsListResult);
        }
        return CommonResult.success(results);
    }
}
