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
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.product.ProductInfoParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.trace.CropsIdParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.CropsVoResult;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.SnowFlake;
import com.yaohy.intelligentfarmingbackend.util.TimeUtil;
import com.yaohy.intelligentfarmingbackend.util.request.ClassCopyUtils;
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
@RequestMapping("/product")
public class TraceProductController {
    @Resource
    HttpServletRequest request;
    @Resource
    UserService userService;
    @Resource
    TransportMapper transportMapper;
    @Resource
    DeptMapper   deptMapper;
    @Resource
    FieldPlantRelationMapper fieldPlantRelationMapper;
    @Resource
    RestTemplate restTemplate;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Web
    @App
    public CommonResult getList() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if(user == null) {
            return CommonResult.fail("账号异常");
        }

        Integer deptId = user.getDeptId();

        List<Transport> list = transportMapper.selectList(new QueryWrapper<Transport>().eq("dept_id", deptId).eq("out_factory_status", 0));

        List<CropsVoResult> cropsVoResults = new ArrayList<>();
        for (Transport transport : list) {
            Long cropsId = transport.getCropsId();
            FieldPlantRelation fieldPlantRelation = fieldPlantRelationMapper.selectOne(new QueryWrapper<FieldPlantRelation>().eq("crops_id", cropsId));
            CropsVoResult result = new CropsVoResult();
            ClassCopyUtils.ClassCopy(result, transport);
            result.setCropsName(fieldPlantRelation.getCropsName());
            result.setOutFactoryStatus(fieldPlantRelation.getOutFactoryStatus());
            result.setMachingStatus(fieldPlantRelation.getMachineStatus());
            result.setProductWriteStatus(fieldPlantRelation.getProductWriteStatus());
            System.out.println(result);
            cropsVoResults.add(result);
        }

        return CommonResult.success(cropsVoResults);
    }

    @RequestMapping(value = "/factoryId", method = RequestMethod.GET)
    @Web
    @App
    public CommonResult getDeptList() {
        List<Dept> list = deptMapper.selectList(new QueryWrapper<Dept>().eq("parent_id", 125));
        return CommonResult.success(list);
    }

    @RequestMapping(value = "/productInfo", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult createProductInfo(@RequestBody ProductInfoParam param){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if(user == null) {
            return CommonResult.fail("账号异常");
        }


        //将cropsId上传到区块链
        List list = new ArrayList();

        Long id = SnowFlake.nextId();

        //id
        list.add(String.valueOf(id));
        //id
        list.add(String.valueOf(id));

        Transport transport = transportMapper.selectById(param.getId());
        if(transport == null) {
            return CommonResult.fail("作物信息异常");
        }
        FieldPlantRelation fieldPlantRelation = fieldPlantRelationMapper.selectOne(new QueryWrapper<FieldPlantRelation>().eq("crops_id", transport.getCropsId()));
        if(fieldPlantRelation == null || fieldPlantRelation.getProductWriteStatus() == 1) {
            return CommonResult.fail("作物信息异常");
        }
        fieldPlantRelation.setProductWriteStatus(1);

        list.add(String.valueOf(transport.getCropsId()));

        list.add(param.getProductName());
        list.add(param.getIngredients());
        list.add(param.getLeader());
        list.add(param.getPhone());
        list.add(param.getWorkshop());
        Dept dept = deptMapper.selectById(user.getDeptId());
        if(dept == null) {
            return CommonResult.fail("部门信息异常");
        }
        list.add(dept.getDeptName());
        list.add(param.getHour());
        list.add(TimeUtil.getNowTime());
        list.add(param.getStorageMethod());
        list.add(param.getNetContent());
        list.add(param.getRecommended());
        list.add(param.getRemark());

        HashMap reqMap = new HashMap<String, Object[]>();
        reqMap.put("productInfoArray", list);

        restTemplate.postForObject(BlockChainConstant.PRODUCT_API+"/createProductInfo", reqMap, String.class);

        fieldPlantRelationMapper.updateById(fieldPlantRelation);

        return CommonResult.success();
    }

    @RequestMapping(value = "/productOutFactory", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult productOutFactory(@RequestBody CropsIdParam cropsIdParam) {
        FieldPlantRelation fieldPlantRelation = fieldPlantRelationMapper.selectOne(new QueryWrapper<FieldPlantRelation>().eq("crops_id", cropsIdParam.getCropsId()));

        fieldPlantRelation.setMachineStatus(2);
        fieldPlantRelation.setOutFactoryStatus(1);

        fieldPlantRelationMapper.updateById(fieldPlantRelation);

        return CommonResult.success();
    }
}
