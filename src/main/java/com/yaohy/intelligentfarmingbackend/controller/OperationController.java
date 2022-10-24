package com.yaohy.intelligentfarmingbackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Operation;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.OperationParam;
import com.yaohy.intelligentfarmingbackend.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OperationController {

    @Autowired
    private OperationService operationService;

//    @App
//    @Web
//    @RequestMapping(value = "/operations",method = RequestMethod.POST)
//    public CommonResult add(@RequestBody OperationParam param){
//        Operation operation = new Operation();
//        BeanUtil.copyProperties(param,operation);
//        operationService.save(operation);
//
//        return CommonResult.success();
//    }
//
//    @App
//    @Web
//    @RequestMapping(value = "/operations",method = RequestMethod.GET)
//    public CommonResult getAll(){
//        List<Operation> operations = operationService.list();
//
//        return CommonResult.success(operations);
//    }



}
