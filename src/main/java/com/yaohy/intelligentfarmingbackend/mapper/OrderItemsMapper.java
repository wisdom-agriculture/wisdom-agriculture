package com.yaohy.intelligentfarmingbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaohy.intelligentfarmingbackend.pojo.domain.OrderItems;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemsMapper extends BaseMapper<OrderItems> {
}
