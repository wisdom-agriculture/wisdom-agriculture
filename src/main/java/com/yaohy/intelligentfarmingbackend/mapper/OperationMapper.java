package com.yaohy.intelligentfarmingbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.Operation;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OperationMapper extends BaseMapper<Operation> {
}
