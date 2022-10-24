package com.yaohy.intelligentfarmingbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
