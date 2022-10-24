package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.EventMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Event;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements com.yaohy.intelligentfarmingbackend.service.EventService {
}
