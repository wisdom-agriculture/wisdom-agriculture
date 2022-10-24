package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.DiseaseMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Disease;
import com.yaohy.intelligentfarmingbackend.service.DiseaseService;
import org.springframework.stereotype.Service;

@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease> implements DiseaseService {
}
