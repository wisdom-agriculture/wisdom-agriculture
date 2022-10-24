package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.SensorTypeMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.SensorType;
import com.yaohy.intelligentfarmingbackend.service.SensorTypeService;
import org.springframework.stereotype.Service;

@Service
public class SensorTypeServiceImpl extends ServiceImpl<SensorTypeMapper, SensorType> implements SensorTypeService {
}
