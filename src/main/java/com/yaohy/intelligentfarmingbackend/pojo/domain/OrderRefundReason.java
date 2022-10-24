package com.yaohy.intelligentfarmingbackend.pojo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class OrderRefundReason {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private String orderNumber;

    private Integer produceId;

    private Integer userId;

    private String userName;

    private String userPhone;

    private String refundReason;

}
