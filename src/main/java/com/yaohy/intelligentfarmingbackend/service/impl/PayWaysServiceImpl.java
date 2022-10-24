package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.PayWaysMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.PayWays;
import com.yaohy.intelligentfarmingbackend.service.PayWaysService;
import org.springframework.stereotype.Service;

@Service
public class PayWaysServiceImpl extends ServiceImpl<PayWaysMapper, PayWays> implements PayWaysService {
}
