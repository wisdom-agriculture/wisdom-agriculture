package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.ProducesSkuMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.ProducesSku;
import com.yaohy.intelligentfarmingbackend.service.ProducesSkuService;
import org.springframework.stereotype.Service;

@Service
public class ProducesSkuServiceImpl extends ServiceImpl<ProducesSkuMapper, ProducesSku> implements ProducesSkuService {
}
