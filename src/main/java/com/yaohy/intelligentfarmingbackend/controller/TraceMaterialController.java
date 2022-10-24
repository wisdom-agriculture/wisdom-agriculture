package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.constant.BlockChainConstant;
import com.yaohy.intelligentfarmingbackend.mapper.DeptMapper;
import com.yaohy.intelligentfarmingbackend.mapper.TransportMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Dept;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Transport;
import com.yaohy.intelligentfarmingbackend.pojo.domain.User;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.trace.CropsIdParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.trace.MaterialOutCropsParam;
import com.yaohy.intelligentfarmingbackend.service.UserService;
import com.yaohy.intelligentfarmingbackend.util.JwtUtil;
import com.yaohy.intelligentfarmingbackend.util.SnowFlake;
import com.yaohy.intelligentfarmingbackend.util.TimeUtil;
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
@RequestMapping("/material")
public class TraceMaterialController {
    @Resource
    HttpServletRequest request;
    @Resource
    UserService userService;
    @Resource
    TransportMapper transportMapper;
    @Resource
    DeptMapper deptMapper;
    @Resource
    RestTemplate restTemplate;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Web
    @App
    public CommonResult getList(){
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if(user == null) {
            return CommonResult.fail("账号异常");
        }

        Integer deptId = user.getDeptId();

        List<Transport> list = transportMapper.selectList(new QueryWrapper<Transport>().eq("dept_id", deptId).eq("status", 2));

        return CommonResult.success(list);
    }

    @RequestMapping(value = "/factoryId", method = RequestMethod.GET)
    @Web
    @App
    public CommonResult getDeptList() {
        List<Dept> list = deptMapper.selectList(new QueryWrapper<Dept>().eq("parent_id", 123));
        return CommonResult.success(list);
    }

    @RequestMapping(value = "/outCrops", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult outCrops(@RequestBody MaterialOutCropsParam param) {
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
        //用户昵称
        list.add(user.getNickname());
        //用户电话
        list.add(user.getPhone());
        //用户部门
        Dept dept = deptMapper.selectById(user.getDeptId());
        if(dept == null) {
            return CommonResult.fail("用户部门异常");
        }
        list.add(dept.getDeptName());
        Transport transport = transportMapper.selectById(param.getId());
        if(transport == null) {
            return CommonResult.fail("作物异常");
        }

        list.add(String.valueOf(transport.getCropsId()));
        list.add(param.getCheckResult());
        list.add(TimeUtil.getTimeByDate(transport.getTime()));
        list.add(TimeUtil.getNowTime());
        list.add(param.getPhoto());
        list.add(param.getRemark());

        HashMap reqMap = new HashMap<String, Object[]>();
        reqMap.put("checkInfoArray", list);

        restTemplate.postForObject(BlockChainConstant.MATERIAL_API+"/createMaching", reqMap, String.class);

        transport.setOutFactoryStatus(1);

        transportMapper.updateById(transport);
        return CommonResult.success();
    }
}
