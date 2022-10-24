package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.PlantStatusMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.PlantStatus;
import com.yaohy.intelligentfarmingbackend.service.PlantStatusService;
import org.springframework.stereotype.Service;

@Service
public class PlantStatusServiceImpl extends ServiceImpl<PlantStatusMapper, PlantStatus> implements PlantStatusService {
}
