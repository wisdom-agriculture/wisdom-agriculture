package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.UserMessageImageMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserMessageImage;
import com.yaohy.intelligentfarmingbackend.service.UserMessageImageService;
import org.springframework.stereotype.Service;

@Service
public class UserMessageImageServiceImpl extends ServiceImpl<UserMessageImageMapper, UserMessageImage> implements UserMessageImageService {
}
