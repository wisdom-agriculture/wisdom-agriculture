package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.PlantMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Plant;
import com.yaohy.intelligentfarmingbackend.service.PlantService;
import org.springframework.stereotype.Service;

@Service
public class PlantServiceImpl extends ServiceImpl<PlantMapper, Plant> implements PlantService {
}
