package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.OrderMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Orders;
import com.yaohy.intelligentfarmingbackend.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
}
