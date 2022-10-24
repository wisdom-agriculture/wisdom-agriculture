package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.SensorDataMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.SensorData;
import com.yaohy.intelligentfarmingbackend.service.SensorDataService;
import org.springframework.stereotype.Service;

@Service
public class SensorDataServiceImpl extends ServiceImpl<SensorDataMapper, SensorData> implements SensorDataService {
}
