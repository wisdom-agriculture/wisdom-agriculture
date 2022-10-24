package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.mapper.DeptMapper;
import com.yaohy.intelligentfarmingbackend.mapper.FieldPlantRelationMapper;
import com.yaohy.intelligentfarmingbackend.mapper.TransportMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Dept;
import com.yaohy.intelligentfarmingbackend.pojo.domain.FieldPlantRelation;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Transport;
import com.yaohy.intelligentfarmingbackend.pojo.domain.User;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.transport.AddTransportParam;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.TimeUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.Date;

/**
 * @author Valentine
 */
@RestController
public class TraceTransportController {
    @Resource
    TransportMapper transportMapper;
    @Resource
    UserService userService;
    @Resource
    HttpServletRequest request;
    @Resource
    FieldPlantRelationMapper fieldPlantRelationMapper;
    @Resource
    DeptMapper deptMapper;

    @RequestMapping(value = "/transport", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult transport(@RequestBody AddTransportParam param) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if (user == null) {
            return CommonResult.fail("账号异常");
        }

        if(insertTransportToDb(param, user)) {
            return CommonResult.success();
        }

        return CommonResult.fail("作物状态异常");
    }

    public boolean insertTransportToDb(AddTransportParam param, User user) {
        FieldPlantRelation crops = fieldPlantRelationMapper.selectOne(new QueryWrapper<FieldPlantRelation>().eq("crops_id", param.getCropsId()));
        if(crops == null) {
            return false;
        }
        Transport transport = new Transport();
        transport.setDeptId(param.getDeptId());
        transport.setCropsId(crops.getCropsId());
        transport.setDriverId(param.getDriverId());
        transport.setFarmerId(user.getId());
        transport.setFarmerTel(user.getPhone());

        Dept dept = deptMapper.selectById(user.getDeptId());
        transport.setFarmerDeptName(dept.getDeptName());
        transport.setFarmerNickname(user.getNickname());
        transport.setTime(TimeUtil.getDate());
        transport.setStatus(0);
        transport.setRemarks(param.getRemarks());
        transport.setOutFactoryStatus(0);

        transportMapper.insert(transport);

        Long cropsId = crops.getCropsId();
        FieldPlantRelation crops_id = fieldPlantRelationMapper.selectOne(new QueryWrapper<FieldPlantRelation>().eq("crops_id", cropsId));
        crops_id.setStatus(1);
        fieldPlantRelationMapper.updateById(crops_id);
        return true;
    }
}
