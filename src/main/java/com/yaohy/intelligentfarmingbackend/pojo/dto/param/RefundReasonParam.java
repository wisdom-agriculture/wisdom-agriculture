package com.yaohy.intelligentfarmingbackend.pojo.dto.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RefundReasonParam {

    private Integer orderId;

    private Integer produceId;

    private String refundReason;

}
