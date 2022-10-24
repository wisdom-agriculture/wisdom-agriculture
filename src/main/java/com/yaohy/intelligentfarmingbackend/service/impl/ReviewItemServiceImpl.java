package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.ReviewItemMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.ReviewItem;
import com.yaohy.intelligentfarmingbackend.service.ReviewItemService;
import org.springframework.stereotype.Service;

@Service
public class ReviewItemServiceImpl extends ServiceImpl<ReviewItemMapper, ReviewItem> implements ReviewItemService {
}
