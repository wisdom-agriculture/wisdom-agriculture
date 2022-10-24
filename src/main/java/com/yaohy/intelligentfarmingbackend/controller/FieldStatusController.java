package com.yaohy.intelligentfarmingbackend.controller;

import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.FieldStatus;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.service.FieldStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fields")
public class FieldStatusController {

    @Autowired
    private FieldStatusService fieldStatusService;

    @App
    @Web
    @RequestMapping(value = "/statuses",method = RequestMethod.GET)
    public CommonResult getAll(){
        List<FieldStatus> fieldStatuses = fieldStatusService.list();

        return CommonResult.success(fieldStatuses);
    }

}
