package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EquipmentOrderCenterResult {

    private List<OrderManagementResult> orderManagementResult;

    @Data
    public static class OrderManagementResult{
        private Integer id;

        private String orderNumber;

        private String producesName;

        private String producesImage;

        private Double amountPayable;

        private Date createTime;

        private Double producesPrice;

        private Double producesWeight;

        private String producesPer;

        private Integer isPay;

        private Integer isPost;
    }

}
