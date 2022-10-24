package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.UserMessageMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserMessage;
import com.yaohy.intelligentfarmingbackend.service.UserMessageService;
import org.springframework.stereotype.Service;

@Service
public class UserMessageServiceImpl extends ServiceImpl<UserMessageMapper, UserMessage> implements UserMessageService {
}
