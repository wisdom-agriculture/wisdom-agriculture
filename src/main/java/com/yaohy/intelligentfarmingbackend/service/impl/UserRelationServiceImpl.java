package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.UserRelationMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserRelation;
import com.yaohy.intelligentfarmingbackend.service.UserRelationService;
import org.springframework.stereotype.Service;

@Service
public class UserRelationServiceImpl extends ServiceImpl<UserRelationMapper, UserRelation> implements UserRelationService {
}
