package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.OrderItemsMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.OrderItems;
import com.yaohy.intelligentfarmingbackend.service.OrderItemsService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemsServiceImpl extends ServiceImpl<OrderItemsMapper, OrderItems> implements OrderItemsService {
}
