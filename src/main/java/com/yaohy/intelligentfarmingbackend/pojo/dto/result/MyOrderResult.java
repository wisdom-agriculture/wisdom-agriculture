package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MyOrderResult {

    private Integer orderId;
    private Date createTime;
    private String shopName;
    private List<MyGoods> myGoodsList;
        @Data
        public static class MyGoods{

            private Integer produceId;
            private String produceName;
            private String produceImage;
            private String producePer;
            private Double produceWeight;

        }

}