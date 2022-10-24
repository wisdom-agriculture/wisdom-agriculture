package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.constant.BlockChainConstant;
import com.yaohy.intelligentfarmingbackend.mapper.DeptMapper;
import com.yaohy.intelligentfarmingbackend.mapper.FieldPlantRelationMapper;
import com.yaohy.intelligentfarmingbackend.mapper.TransportMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Dept;
import com.yaohy.intelligentfarmingbackend.pojo.domain.FieldPlantRelation;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Transport;
import com.yaohy.intelligentfarmingbackend.pojo.domain.User;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.RetailerReceiveParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.trace.CropsIdParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.CropsVoResult;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.SnowFlake;
import com.yaohy.intelligentfarmingbackend.util.request.ClassCopyUtils;
import com.yaohy.intelligentfarmingbackend.util.request.RequestUtils;
import com.yaohy.intelligentfarmingbackend.util.request.dto.Param;
import org.simpleframework.xml.Path;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Valentine
 */
@RestController
@RequestMapping("/retailer")
public class TraceRetailerController {
    @Resource
    HttpServletRequest request;
    @Resource
    UserService userService;
    @Resource
    TransportMapper transportMapper;
    @Resource
    FieldPlantRelationMapper fieldPlantRelationMapper;
    @Resource
    DeptMapper deptMapper;
    @Resource
    RestTemplate restTemplate;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Web
    @App
    public CommonResult getList() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if (user == null) {
            return CommonResult.fail("账号异常");
        }

        Integer deptId = user.getDeptId();


        List<Transport> list = transportMapper.selectList(new QueryWrapper<Transport>().eq("dept_id", deptId).eq("out_factory_status", 0));

        List<CropsVoResult> cropsVoResults = new ArrayList<>();
        if(!list.isEmpty()) {
            for (Transport transport : list) {
                Long cropsId = transport.getCropsId();
                CropsVoResult result = new CropsVoResult();
                FieldPlantRelation fieldPlantRelation = fieldPlantRelationMapper.selectOne(new QueryWrapper<FieldPlantRelation>().eq("crops_id", cropsId));
                ClassCopyUtils.ClassCopy(result, transport);
                result.setCropsName(fieldPlantRelation.getCropsName());
                result.setOutFactoryStatus(fieldPlantRelation.getOutFactoryStatus());
                result.setMachingStatus(fieldPlantRelation.getMachineStatus());
                result.setProductWriteStatus(fieldPlantRelation.getProductWriteStatus());
                cropsVoResults.add(result);
            }
        }

        return CommonResult.success(cropsVoResults);
    }

    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult receive(@RequestBody RetailerReceiveParam param) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if (user == null) {
            return CommonResult.fail("账号异常");
        }

        Transport transport = transportMapper.selectById(param.getId());
        if(transport == null) {
            return CommonResult.fail("数据库异常");
        }
        //将cropsId上传到区块链
        List list = new ArrayList();

        Long id = SnowFlake.nextId();

        //id
        list.add(String.valueOf(id));
        //id
        list.add(String.valueOf(id));
        list.add(String.valueOf(transport.getCropsId()));
        list.add(user.getNickname());
        list.add(String.valueOf(user.getId()));
        list.add(user.getPhone());
        Dept dept = deptMapper.selectById(user.getDeptId());
        if (dept == null) {
            return CommonResult.fail("部门信息异常");
        }
        list.add(dept.getDeptName());
        list.add("零售商售卖");

        HashMap reqMap = new HashMap<String, Object[]>();
        reqMap.put("retailerArray", list);

        restTemplate.postForObject(BlockChainConstant.RETAILER_API + "/createRetailer", reqMap, String.class);

        transport.setRetailerReceiveStatus(1);

        transportMapper.updateById(transport);
        return CommonResult.success();
    }

    @Web
    @App
    @RequestMapping(value = "/queryRetailer", method = RequestMethod.GET)
    public CommonResult queryRetailer(@RequestParam Long cropsId) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if (user == null) {
            return CommonResult.fail("账号异常");
        }
        Object object = RequestUtils.GET(BlockChainConstant.RETAILER_API + "/queryRetailerByCropsId", new Param("cropsId", cropsId,"retailerId",user.getId()));

        return CommonResult.success(object);
    }
}
