package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MyOrderListResult {

    private List<MyOrder> myOrderList;

    @Data
    public static class MyOrder{

        private Integer id;

        private String orderNumber;

        private Double producesPrice;

        private Double producesWeight;

        private Double allPay;

        private Date createTime;

        private Integer isPay;

        private Integer isPost;

        private String url;

    }

}
