package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.ZoneMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Zone;
import com.yaohy.intelligentfarmingbackend.service.ZoneService;
import org.springframework.stereotype.Service;

@Service
public class ZoneServiceImpl extends ServiceImpl<ZoneMapper, Zone> implements ZoneService {
}
