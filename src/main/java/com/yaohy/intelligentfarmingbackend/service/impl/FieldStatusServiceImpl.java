package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.FieldStatusMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.FieldStatus;
import com.yaohy.intelligentfarmingbackend.service.FieldStatusService;
import org.springframework.stereotype.Service;

@Service
public class FieldStatusServiceImpl extends ServiceImpl<FieldStatusMapper, FieldStatus> implements FieldStatusService {
}
