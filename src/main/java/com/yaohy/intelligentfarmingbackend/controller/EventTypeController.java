package com.yaohy.intelligentfarmingbackend.controller;

import cn.hutool.core.bean.BeanUtil;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Event;
import com.yaohy.intelligentfarmingbackend.pojo.domain.EventType;
import com.yaohy.intelligentfarmingbackend.pojo.dto.CommonResult;
import com.yaohy.intelligentfarmingbackend.pojo.dto.param.EventTypeParam;
import com.yaohy.intelligentfarmingbackend.service.EventService;
import com.yaohy.intelligentfarmingbackend.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @App
    @Web
    @RequestMapping(value = "/types",method = RequestMethod.POST)
    public CommonResult add(@RequestParam EventTypeParam param){
        EventType eventType = new EventType();
        BeanUtil.copyProperties(param,eventType);
        eventTypeService.save(eventType);

        return CommonResult.success();
    }

    @App
    @Web
    @RequestMapping(value = "/types",method = RequestMethod.GET)
    public CommonResult getAll(){
        //TODO 按照用户id搜索土地 目前这里会返回所有土地
        List<EventType> eventTypeList = eventTypeService.list();

        return CommonResult.success(eventTypeList);
    }


}
