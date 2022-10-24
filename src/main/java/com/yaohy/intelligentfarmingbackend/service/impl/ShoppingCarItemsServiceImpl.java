package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.ShoppingCarItemsMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.ShoppingCarItems;
import com.yaohy.intelligentfarmingbackend.service.ShoppingCarItemsService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCarItemsServiceImpl extends ServiceImpl<ShoppingCarItemsMapper, ShoppingCarItems> implements ShoppingCarItemsService {
}
