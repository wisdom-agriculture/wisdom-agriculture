package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.OperationMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Operation;
import com.yaohy.intelligentfarmingbackend.service.OperationService;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl extends ServiceImpl<OperationMapper, Operation> implements OperationService {
}
