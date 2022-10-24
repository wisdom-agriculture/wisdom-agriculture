package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.GoodItemMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.GoodItem;
import com.yaohy.intelligentfarmingbackend.service.GoodItemService;
import org.springframework.stereotype.Service;

@Service
public class GoodItemServiceImpl extends ServiceImpl<GoodItemMapper, GoodItem> implements GoodItemService {
}
