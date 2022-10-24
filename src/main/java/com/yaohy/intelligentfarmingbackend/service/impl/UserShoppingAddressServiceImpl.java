package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.UserShoppingAddressMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserShoppingAddress;
import com.yaohy.intelligentfarmingbackend.service.UserShoppingAddressService;
import org.springframework.stereotype.Service;

@Service
public class UserShoppingAddressServiceImpl extends ServiceImpl<UserShoppingAddressMapper, UserShoppingAddress> implements UserShoppingAddressService {
}
