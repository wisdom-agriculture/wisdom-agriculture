package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.FieldMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Field;
import com.yaohy.intelligentfarmingbackend.service.FieldService;
import org.springframework.stereotype.Service;

@Service
public class FieldServiceImpl extends ServiceImpl<FieldMapper, Field> implements FieldService {
}
