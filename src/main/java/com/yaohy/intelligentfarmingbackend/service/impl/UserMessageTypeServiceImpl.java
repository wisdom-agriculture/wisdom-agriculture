package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.UserMessageMapper;
import com.yaohy.intelligentfarmingbackend.mapper.UserMessageTypeMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserMessage;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserMessageType;
import com.yaohy.intelligentfarmingbackend.service.UserMessageTypeService;
import org.springframework.stereotype.Service;

@Service
public class UserMessageTypeServiceImpl extends ServiceImpl<UserMessageTypeMapper, UserMessageType> implements UserMessageTypeService {

}
