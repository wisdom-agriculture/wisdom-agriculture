package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.UserRoleRelationMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserRoleRelation;
import com.yaohy.intelligentfarmingbackend.service.UserRoleRelationService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleRelationServiceImpl extends ServiceImpl<UserRoleRelationMapper, UserRoleRelation> implements UserRoleRelationService {
}
