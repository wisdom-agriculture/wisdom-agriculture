package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.PlantAttributeMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.PlantAttribute;
import com.yaohy.intelligentfarmingbackend.service.PlantAttributeService;
import org.springframework.stereotype.Service;

@Service
public class PlantAttributeServiceImpl extends ServiceImpl<PlantAttributeMapper, PlantAttribute> implements PlantAttributeService {
}
