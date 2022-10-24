package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.ZoneCommentMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.ZoneComment;
import com.yaohy.intelligentfarmingbackend.service.ZoneCommentService;
import org.springframework.stereotype.Service;

@Service
public class ZoneCommentServiceImpl extends ServiceImpl<ZoneCommentMapper, ZoneComment> implements ZoneCommentService {
}
