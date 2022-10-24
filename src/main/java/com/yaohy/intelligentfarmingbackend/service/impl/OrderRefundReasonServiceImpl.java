package com.yaohy.intelligentfarmingbackend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.OrderRefundReasonMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.OrderRefundReason;
import com.yaohy.intelligentfarmingbackend.service.OrderRefundReasonService;
import org.springframework.stereotype.Service;

@Service
public class OrderRefundReasonServiceImpl extends ServiceImpl<OrderRefundReasonMapper, OrderRefundReason> implements OrderRefundReasonService {
}
