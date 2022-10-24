package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.ShoppingCarMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.ShoppingCar;
import com.yaohy.intelligentfarmingbackend.service.ShoppingCarService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCarServiceImpl extends ServiceImpl<ShoppingCarMapper, ShoppingCar> implements ShoppingCarService {
}
