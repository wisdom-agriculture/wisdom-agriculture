package com.yaohy.intelligentfarmingbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.constant.BlockChainConstant;
import com.yaohy.intelligentfarmingbackend.mapper.DeptMapper;
import com.yaohy.intelligentfarmingbackend.mapper.TransportMapper;
import com.yaohy.intelligentfarmingbackend.mapper.UserMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Dept;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Transport;
import com.yaohy.intelligentfarmingbackend.pojo.domain.User;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.trace.TransportIdParam;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.DriversResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.result.NoData;
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
@RequestMapping("/driver")
@RestController
public class TraceDriverController {
    @Resource
    HttpServletRequest request;
    @Resource
    UserService userService;
    @Resource
    TransportMapper transportMapper;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DeptMapper deptMapper;

    @RequestMapping(value = "/drivers", method = RequestMethod.GET)
    @Web
    @App
    public CommonResult getDriver() {
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().eq("dept_id", 112));

        List<DriversResult> driversResults = new ArrayList<>();
        for (User user : userList) {
            DriversResult driversResult = new DriversResult();
            ClassCopyUtils.ClassCopy(driversResult, user);
            driversResults.add(driversResult);
        }
        return CommonResult.success(driversResults);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Web
    @App
    public CommonResult list() {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if (user == null) {
            return CommonResult.fail("账号异常");
        }

        List<Transport> list = transportMapper.selectList(new QueryWrapper<Transport>().eq("driver_id", userId));

        return CommonResult.success(list);
    }

    @RequestMapping(value = "/beginTransport", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult<NoData> beginTransport(@RequestBody TransportIdParam id) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if (user == null) {
            return CommonResult.fail("账号异常");
        }

        Transport transport = transportMapper.selectById(id.getId());
        if(transport == null) {
            return CommonResult.fail("数据库异常");
        }
        //添加至区块链
        addToChain(transport, user,transport.getCropsId(), transport.getFarmerDeptName());

        transport.setStatus(1);
        //更新数据库
        transportMapper.updateById(transport);

        return CommonResult.success();
    }

    @RequestMapping(value = "/finishTransport", method = RequestMethod.POST)
    @Web
    @App
    public CommonResult<NoData> finishTransport(@RequestBody TransportIdParam id) {
        Integer userId = JwtUtil.decodeId(request.getHeader("Authorization"));

        User user = userService.getById(userId);
        if (user == null) {
            return CommonResult.fail("账号异常");
        }

        Transport transport = transportMapper.selectById(id.getId());
        if(transport == null || transport.getStatus() != 1) {
            return CommonResult.fail("数据库异常");
        }
        //添加至区块链
        addToChain(transport, user,transport.getCropsId(), "终点");

        transport.setStatus(2);
        //更新数据库
        transportMapper.updateById(transport);

        return CommonResult.success();
    }




    public void addToChain(Transport transport, User user, Long cropsId, String address) {
        long nextId = SnowFlake.nextId();

        List list = new ArrayList();

        list.add(String.valueOf(nextId));
        list.add(String.valueOf(nextId));
        list.add(user.getNickname());
        list.add(user.getNickname());
        list.add(String.valueOf(user.getPhone()));

        Dept dept = deptMapper.selectById(user.getDeptId());
        list.add(dept.getDeptName());

        list.add(TimeUtil.getNowTime());
        Dept toDept = deptMapper.selectById(transport.getDeptId());
        list.add(toDept.getDeptName());
        list.add(String.valueOf(cropsId));
        list.add(address);

        HashMap reqMap = new HashMap<String, Object[]>();
        System.out.println(list);
        reqMap.put("transportArray", list);

        System.out.println(reqMap);

        restTemplate.postForObject(BlockChainConstant.Driver_API + "/createTransport", reqMap, String.class);
    }
}
