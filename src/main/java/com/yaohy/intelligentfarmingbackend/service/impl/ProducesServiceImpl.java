package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.ProducesInfoMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.ProducesInfo;
import com.yaohy.intelligentfarmingbackend.service.ProducesService;
import org.springframework.stereotype.Service;

@Service
public class ProducesServiceImpl extends ServiceImpl<ProducesInfoMapper, ProducesInfo> implements ProducesService {
}
