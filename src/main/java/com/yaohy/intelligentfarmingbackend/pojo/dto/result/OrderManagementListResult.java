package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderManagementListResult {

    private List<OrderManagementResult> orderManagementResult;

    @Data
    public static class OrderManagementResult{
        private Integer id;

        private String orderNumber;

        private Double amountPayable;

        private Date createTime;

        private Integer isPay;

        private Integer isPost;
    }

}
