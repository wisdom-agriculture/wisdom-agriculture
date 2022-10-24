package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.EventTypeMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.EventType;
import com.yaohy.intelligentfarmingbackend.service.EventTypeService;
import org.springframework.stereotype.Service;

@Service
public class EventTypeServiceImpl extends ServiceImpl<EventTypeMapper, EventType> implements EventTypeService {
}
