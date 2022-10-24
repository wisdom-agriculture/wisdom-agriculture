package com.yaohy.intelligentfarmingbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaohy.intelligentfarmingbackend.mapper.UserFavoritesMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.UserFavorites;
import com.yaohy.intelligentfarmingbackend.service.UserFavoritesService;
import org.springframework.stereotype.Service;

@Service
public class UserFavoritesServiceImpl extends ServiceImpl<UserFavoritesMapper, UserFavorites> implements UserFavoritesService {
}
