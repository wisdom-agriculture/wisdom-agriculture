package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.SensorMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Sensor;
import com.yaohy.intelligentfarmingbackend.service.SensorService;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl extends ServiceImpl<SensorMapper, Sensor> implements SensorService {
}
